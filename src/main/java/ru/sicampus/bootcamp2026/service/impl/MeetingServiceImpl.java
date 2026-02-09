package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingInputDTO;
import ru.sicampus.bootcamp2026.dto.MemberDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.entity.UsersMeeting;
import ru.sicampus.bootcamp2026.entity.UsersMeetingStatus;
import ru.sicampus.bootcamp2026.exceptions.AlreadyExistMeetingAtThisTimeException;
import ru.sicampus.bootcamp2026.exceptions.MeetingNotExistException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UsersMeetingRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.MemberMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final UsersMeetingRepository usersMeetingRepository;

    @Override
    public List<MeetingDTO> getAllMeeting() {
        return meetingRepository.findAll()
                .stream()
                .map(MeetingMapper::convertToDto)
                .toList();
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExistException("Meeting with like this id don't exist"));
        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    public MeetingDTO createMeeting(Users user, MeetingInputDTO dto) {
        LocalDateTime start = dto.getStart();
        LocalDateTime end = dto.getStart().plusMinutes(dto.getDuration());
        List<Long> overlappingMeeting = meetingRepository.findOverlappingMeetingIds(start, end, user.getId());
        if (!overlappingMeeting.isEmpty()) {
            throw new AlreadyExistMeetingAtThisTimeException("New meeting overlaps with another meeting");
        }

        Meeting meeting = new Meeting();
        meeting.setDuration(dto.getDuration());
        meeting.setStart(dto.getStart());
        meeting.setPlace(dto.getPlace());
        meeting.setTheme(dto.getTheme());
        meeting.setDescription(dto.getDescription());
        meeting.setCreator(user);
        MeetingDTO response = MeetingMapper.convertToDto(meetingRepository.save(meeting));

        UsersMeeting relation = new UsersMeeting();
        relation.setMeeting(meeting);
        relation.setMember(user);
        relation.setStatus(UsersMeetingStatus.CREATOR);
        usersMeetingRepository.save(relation);
        return response;
    }

    @Override
    public MeetingDTO updateMeeting(Long id, Users user, MeetingInputDTO dto) {
        List<Long> overlappingMeeting = meetingRepository.findOverlappingMeetingIds(dto.getStart(), dto.getStart().plusMinutes(dto.getDuration()), user.getId());
        if (!overlappingMeeting.isEmpty() && (overlappingMeeting.size() > 1 || !overlappingMeeting.get(0).equals(id))) {
            throw new AlreadyExistMeetingAtThisTimeException("New meeting overlaps with another meeting");
        }

        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExistException("Meeting not exist"));
        meeting.setDuration(dto.getDuration());
        meeting.setStart(dto.getStart());
        meeting.setPlace(dto.getPlace());
        meeting.setTheme(dto.getTheme());
        meeting.setDescription(dto.getDescription());
        meeting.setCreator(user);
        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.delete(meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExistException("Meeting with like this id don't exist")));
    }

    @Override
    public List<MemberDTO> getAllMemberOfMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExistException("Meeting with like this id don't exist"));
        return meeting.getMembers()
                .stream()
                .map((UM) -> MemberMapper.convertToDto(
                        UserMapper.convertToDto(UM.getMember()),
                        UM.getStatus()
                ))
                .toList();
    }

    @Override
    public Page<MeetingDTO> getAllMeetingPaginated(Pageable pageable) {
        return meetingRepository.findAll(pageable).map(MeetingMapper::convertToDto);
    }

    @Override
    public Pageable buildPage(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public List<MeetingDTO> getAllMeetingAfterNow(Users user) {
        return meetingRepository.findAllAfterNow(user.getId()).stream().map(MeetingMapper::convertToDto).toList();
    }
}
