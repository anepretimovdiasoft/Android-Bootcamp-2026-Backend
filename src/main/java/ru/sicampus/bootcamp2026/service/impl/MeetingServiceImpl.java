package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.CreateMeetingRequestDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.PersonNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.TimeValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final PersonRepository PersonRepository;

    @Override
    public MeetingDTO createMeeting(CreateMeetingRequestDTO request) {
        TimeValidator.validateMeetingTime(request.getStartTime(), request.getEndTime());

        Person organizer = PersonRepository
                .findById(request.getOrganizerId())
                .orElseThrow(PersonNotFoundException::new);

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
        Meeting meeting = meetingRepository
                .findById(id)
                .orElseThrow(MeetingNotFoundException::new);
        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    public void deleteMeeting(Long id) {
        if (!meetingRepository.existsById(id)) {
            throw new MeetingNotFoundException();
        }
        meetingRepository.deleteById(id);
    }

    @Override
    public List<MeetingDTO> getPersonMeetings(Long PersonId) {
        if (!PersonRepository.existsById(PersonId)) {
            throw new RuntimeException("Person not found");
        }

        List<Meeting> meetings = meetingRepository.findByOrganizerId(PersonId);

        return meetings.stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }
}