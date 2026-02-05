package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public List<MeetingDTO> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found!"));
        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    public MeetingDTO createMeeting(MeetingDTO dto) {
        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new UserNotFoundException("Organizer not found!"));

        Meeting meeting = new Meeting();
        meeting.setTitle(dto.getTitle());
        meeting.setOrganizer(organizer);
        meeting.setDate(dto.getDate());
        meeting.setStartHour(dto.getStartHour());
        meeting.setCreatedAt(LocalDateTime.now());

        Meeting saved = meetingRepository.save(meeting);
        return MeetingMapper.convertToDto(saved);
    }

    @Override
    public MeetingDTO updateMeeting(Long id, MeetingDTO dto) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found!"));

        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new UserNotFoundException("Organizer not found!"));

        meeting.setTitle(dto.getTitle());
        meeting.setOrganizer(organizer);
        meeting.setDate(dto.getDate());
        meeting.setStartHour(dto.getStartHour());

        Meeting saved = meetingRepository.save(meeting);
        return MeetingMapper.convertToDto(saved);
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
