package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.exception.WrongDateFormatException;
import ru.sicampus.bootcamp2026.exception.WrongTimeFormatException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;
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
    public Page<MeetingDTO> getAllMeetingsByInvitedUserIdPaginated(Long id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        List<MeetingDTO> meetings = new ArrayList<>();
        invites.forEach(invite -> {
            if (!invite.isAccepted()) {
                meetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), meetings.size());

        List<MeetingDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = meetings.subList(start, end);
        }

        pageContent.sort(Comparator.comparing(MeetingDTO::getDate).thenComparing(dto -> dto.getStartTime().toLocalTime()));

        return new PageImpl<>(pageContent, pageable, meetings.size());
    }

    @Override
    public List<MeetingDTO> getAllPlannedMeetingsByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<MeetingDTO> plannedMeetings = meetingRepository.findAllByCreator(user).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        invites.forEach(invite -> {
            if (invite.isAccepted()) {
                plannedMeetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        return plannedMeetings;
    }

    @Override
    public Page<MeetingDTO> getAllPlannedMeetingsByUserIdPaginated(Long id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<MeetingDTO> plannedMeetings = meetingRepository.findAllByCreator(user).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        invites.forEach(invite -> {
            if (invite.isAccepted()) {
                plannedMeetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), plannedMeetings.size());

        List<MeetingDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = plannedMeetings.subList(start, end);
        }

        pageContent.sort(Comparator.comparing(MeetingDTO::getDate).thenComparing(dto -> dto.getStartTime().toLocalTime()));

        return new PageImpl<>(pageContent, pageable, plannedMeetings.size());
    }

    @Override
    public Page<MeetingDTO> getAllPlannedMeetingsByUserIdAndDatePaginated(Long id, String dateString, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDate filterDate;
        try {
            filterDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new WrongDateFormatException("Wrong dateString format");
        }

        List<MeetingDTO> plannedMeetings = new ArrayList<>();

        List<Meeting> createdByUser = meetingRepository.findAllByCreator(user);
        createdByUser.forEach(meeting -> {
            Date date = meeting.getDate();
            LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.of("UTC"));      //не меняем дату: она уже в правильном часовом поясе
            if (localDate.equals(filterDate)) {
                plannedMeetings.add(MeetingMapper.convertToDto(meeting));
            }
        });

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        invites.forEach(invite -> {
            Date meetingDate = invite.getMeeting().getDate();
            LocalDate localDate = LocalDate.ofInstant(meetingDate.toInstant(), ZoneId.of("UTC"));      //не меняем дату: она уже в правильном часовом поясе
            if (invite.isAccepted() && localDate.equals(filterDate)) {
                plannedMeetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), plannedMeetings.size());

        List<MeetingDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = plannedMeetings.subList(start, end);
        }

        pageContent.sort(Comparator.comparing(MeetingDTO::getDate).thenComparing(dto -> dto.getStartTime().toLocalTime()));

        return new PageImpl<>(pageContent, pageable, plannedMeetings.size());
    }

    @Override
    public Page<MeetingDTO> getAllPlannedMeetingsByUserIdAndDatePeriodPaginated(Long id, String datePeriodStart, String datePeriodEnd, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDate filterStartDate;
        LocalDate filterEndDate;
        try {
            filterStartDate = LocalDate.parse(datePeriodStart);
            filterEndDate = LocalDate.parse(datePeriodEnd);
        } catch (Exception e) {
            throw new WrongDateFormatException("Wrong dateString format");
        }

        List<MeetingDTO> plannedMeetings = new ArrayList<>();
        List<Meeting> createdByUser = meetingRepository.findAllByCreator(user);
        createdByUser.forEach(meeting -> {
            Date meetingDate = meeting.getDate();
            LocalDate localDate = LocalDate.ofInstant(meetingDate.toInstant(), ZoneId.of("UTC"));
            if (localDate.compareTo(filterStartDate) >= 0 && localDate.compareTo(filterEndDate) <= 0) {
                plannedMeetings.add(MeetingMapper.convertToDto(meeting));
            }
        });

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        invites.forEach(invite -> {
            Meeting meeting = invite.getMeeting();
            Date meetingDate = meeting.getDate();
            LocalDate localDate = LocalDate.ofInstant(meetingDate.toInstant(), ZoneId.of("UTC"));
            if (invite.isAccepted() && localDate.compareTo(filterStartDate) >= 0 && localDate.compareTo(filterEndDate) <= 0) {
                plannedMeetings.add(MeetingMapper.convertToDto(meeting));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), plannedMeetings.size());

        List<MeetingDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = plannedMeetings.subList(start, end);
        }

        pageContent.sort(Comparator.comparing(MeetingDTO::getDate).thenComparing(dto -> dto.getStartTime().toLocalTime()));

        return new PageImpl<>(pageContent, pageable, plannedMeetings.size());
    }

    @Override
    public Page<MeetingDTO> getAllMeetingsByInvitedUserIdAndDatePaginated(Long id, String dateString, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDate filterDate;
        try {
            filterDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new WrongDateFormatException("Wrong dateString format");
        }

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        List<MeetingDTO> meetings = new ArrayList<>();
        invites.forEach(invite -> {
            Date date = invite.getMeeting().getDate();
            LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.of("UTC"));      //не меняем дату: она уже в правильном часовом поясе
            if (!(invite.isAccepted()) && localDate.equals(filterDate)) {
                meetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), meetings.size());

        List<MeetingDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = meetings.subList(start, end);
        }

        pageContent.sort(Comparator.comparing(MeetingDTO::getDate).thenComparing(dto -> dto.getStartTime().toLocalTime()));

        return new PageImpl<>(pageContent, pageable, meetings.size());
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
