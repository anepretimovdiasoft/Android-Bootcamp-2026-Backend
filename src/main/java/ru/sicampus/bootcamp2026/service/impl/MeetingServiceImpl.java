package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.CreateMeetingRequestDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.PersonMapper;
import ru.sicampus.bootcamp2026.util.TimeValidator;
import ru.sicampus.bootcamp2026.util.checkers.IdChecker;
import ru.sicampus.bootcamp2026.util.checkers.MeetingChecker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    @Override
    public List<MeetingDTO> getAllMeetings() {
        return meetingRepository.findAll().stream().map(MeetingMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getMeetingsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return meetingRepository.findByStartTimeBetween(start, end)
                .stream()
                .map(MeetingMapper::convertToDto)
                .toList();
    }

    @Override
    public MeetingDTO createMeeting(CreateMeetingRequestDTO request) {
        TimeValidator.validateMeetingTime(request.getStartTime(), request.getEndTime());

        Person organizer = IdChecker.checkId(personRepository, request.getOrganizerId());

        Meeting meeting = new Meeting();
        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setOrganizer(organizer);
        meeting.setStartTime(request.getStartTime());
        meeting.setEndTime(request.getEndTime());
        meeting.setCreatedAt(LocalDateTime.now());

        Meeting savedMeeting = meetingRepository.save(meeting);

        return MeetingMapper.convertToDto(savedMeeting);
    }

    @Override
    public MeetingDTO getMeeting(Long id) {
        Meeting meeting = MeetingChecker.checkMeeting(meetingRepository, id);

        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    public void deleteMeeting(Long id) {
        MeetingChecker.checkMeeting(meetingRepository, id);
        meetingRepository.deleteById(id);
    }

    @Override
    public List<MeetingDTO> getPersonMeetings(Long personId) {
        IdChecker.checkId(personRepository, personId);

        List<Meeting> meetings = meetingRepository.findByOrganizerId(personId);

        return meetings.stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }
}