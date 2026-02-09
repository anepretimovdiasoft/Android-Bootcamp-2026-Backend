package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingOverlapException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public Page<MeetingDTO> getAllMeetings(Pageable pageable) {
        return meetingRepository.findAll(pageable)
                .map(MeetingMapper::convertToDto);
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    public MeetingDTO createMeeting(MeetingDTO dto) {
        if (!meetingRepository.findByDateAndStartHour(dto.getDate(), dto.getStartHour()).isEmpty()) {
            throw new MeetingOverlapException("На это время уже запланирована встреча!");
        }

        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new UserNotFoundException("Organizer not found"));

        Meeting meeting = new Meeting();
        meeting.setTitle(dto.getTitle());
        meeting.setOrganizer(organizer);
        meeting.setDate(dto.getDate());
        meeting.setStartHour(dto.getStartHour());
        meeting.setCreatedAt(LocalDateTime.now());

        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public MeetingDTO updateMeeting(Long id, MeetingDTO dto) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new UserNotFoundException("Organizer not found"));

        meeting.setTitle(dto.getTitle());
        meeting.setOrganizer(organizer);
        meeting.setDate(dto.getDate());
        meeting.setStartHour(dto.getStartHour());

        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public Page<MeetingDTO> getMeetingsByOrganizer(Long organizerId, Pageable pageable) {
        return meetingRepository.findByOrganizerId(organizerId, pageable)
                .map(MeetingMapper::convertToDto);
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
