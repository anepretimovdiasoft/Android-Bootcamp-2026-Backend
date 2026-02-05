package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingInputDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exceptions.AlreadyExistMeetingAtThisTimeException;
import ru.sicampus.bootcamp2026.exceptions.MeetingNotExist;
import ru.sicampus.bootcamp2026.exceptions.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final UsersRepository usersRepository;

    @Override
    public List<MeetingDTO> getAllMeeting() {
        return meetingRepository.findAll()
                .stream()
                .map(MeetingMapper::convertToDto)
                .toList();
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExist("Meeting with like this id don't exist"));
        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    public MeetingDTO createMeeting(Long userId, MeetingInputDTO dto) {
        // TODO: добавлять связь запись в UsersMeeting
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not exist"));
        LocalDateTime start = dto.getStart();
        LocalDateTime end = dto.getStart().plusMinutes(dto.getDuration());
        List<Long> overlappingMeeting = meetingRepository.findOverlappingMeetingIds(start, end, userId);
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
        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public MeetingDTO updateMeeting(Long id, Long userId, MeetingInputDTO dto) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not exist"));
        List<Long> overlappingMeeting = meetingRepository.findOverlappingMeetingIds(dto.getStart(), dto.getStart().plusMinutes(dto.getDuration()), userId);
        if (!overlappingMeeting.isEmpty() && (overlappingMeeting.size() > 1 || !overlappingMeeting.get(0).equals(id))) {
            throw new AlreadyExistMeetingAtThisTimeException("New meeting overlaps with another meeting");
        }

        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExist("Meeting not exist"));
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
        meetingRepository.delete(meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExist("Meeting with like this id don't exist")));
    }

    @Override
    public List<UserDTO> getAllMemberOfMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotExist("Meeting with like this id don't exist"));
        return meeting.getMembers()
                .stream()
                .map((UM) -> UserMapper.convertToDto(UM.getMember()))
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
}
