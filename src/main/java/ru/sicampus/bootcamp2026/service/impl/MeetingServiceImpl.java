package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public MeetingDTO createMeeting(MeetingDTO dto) {
        Meeting meeting = new Meeting();

        Optional<User> creator = userRepository.findById(dto.getCreatorId());
        if (creator.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        meeting.setCreator(creator.get());

        meeting.setTitle(dto.getTitle());
        meeting.setDate(dto.getDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());

        meeting.setInvites(new ArrayList<>());

        Meeting savedMeeting = meetingRepository.save(meeting);

        List<Invitation> invitations = new ArrayList<>();
        List<Long> invitedUserIds = dto.getInvitedUserIds();
        invitedUserIds.forEach(id -> {
            Invitation newInvitation = new Invitation();
            newInvitation.setInvitedUser(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found")));
            newInvitation.setMeeting(savedMeeting);
            newInvitation.setAccepted(false);
            invitations.add(newInvitation);
        });

        savedMeeting.getInvites().addAll(invitations);

        return MeetingMapper.convertToDto(meetingRepository.save(savedMeeting));
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        return meetingRepository.findById(id)
                .map(MeetingMapper::convertToDto)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
    }

    @Override
    public List<MeetingDTO> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MeetingDTO> getAllMeetingsPaginated(Pageable pageable) {
        return meetingRepository.findAll(pageable).map(MeetingMapper::convertToDto);
    }

    @Override
    public List<MeetingDTO> getAllMeetingsByTitle(String title) {
        return meetingRepository.findAllByTitle(title).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getAllMeetingsByInvitedUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        List<MeetingDTO> meetings = new ArrayList<>();
        invites.forEach(invite -> {
            if (!invite.isAccepted()) {
                meetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        return meetings;
    }

    @Override
    public List<MeetingDTO> getAllPlannedMeetingsByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Meeting thisMeeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        List<MeetingDTO> meetings = new ArrayList<>();
        if (thisMeeting.getCreator() == user) {
            meetings.add(MeetingMapper.convertToDto(thisMeeting));
        }
        invites.forEach(invite -> {
            if (invite.isAccepted()) {
                meetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        return meetings;
    }

    @Override
    public MeetingDTO updateMeeting(Long id, MeetingDTO dto) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));

        Optional<User> creator = userRepository.findById(dto.getCreatorId());
        if (creator.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        meeting.setCreator(creator.get());

        meeting.setTitle(dto.getTitle());
        meeting.setDate(dto.getDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());

        List<Invitation> invitations = new ArrayList<>();
        List<Long> updatedInvitedUserIds = dto.getInvitedUserIds();
        updatedInvitedUserIds.forEach(userId -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
            Invitation newInvitation = new Invitation();
            newInvitation.setInvitedUser(user);
            newInvitation.setMeeting(meeting);
            newInvitation.setAccepted(false);
            invitations.add(newInvitation);
        });

        meeting.getInvites().clear();
        meeting.getInvites().addAll(invitations);

        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
