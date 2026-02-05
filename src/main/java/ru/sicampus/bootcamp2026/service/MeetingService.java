package ru.sicampus.bootcamp2026.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import ru.sicampus.bootcamp2026.api.dto.MeetingRequest;
import ru.sicampus.bootcamp2026.api.error.BadRequestException;
import ru.sicampus.bootcamp2026.api.error.NotFoundException;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    public MeetingService(MeetingRepository meetingRepository, PersonRepository personRepository) {
        this.meetingRepository = meetingRepository;
        this.personRepository = personRepository;
    }

    public List<Meeting> list() {
        return meetingRepository.findAll();
    }

    public Meeting get(Long id) {
        return meetingRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Meeting not found: " + id));
    }

    public Meeting create(MeetingRequest request) {
        Meeting meeting = new Meeting();
        apply(meeting, request);
        meeting.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return meetingRepository.save(meeting);
    }

    public Meeting update(Long id, MeetingRequest request) {
        Meeting meeting = get(id);
        apply(meeting, request);
        return meetingRepository.save(meeting);
    }

    public void delete(Long id) {
        if (!meetingRepository.existsById(id)) {
            throw new NotFoundException("Meeting not found: " + id);
        }
        meetingRepository.deleteById(id);
    }

    private void apply(Meeting meeting, MeetingRequest request) {
        if (request.endAt().isBefore(request.startAt())) {
            throw new BadRequestException("endAt must be after startAt");
        }
        Person organizer = personRepository.findById(request.organizerId())
            .orElseThrow(() -> new NotFoundException("Organizer not found: " + request.organizerId()));
        meeting.setOrganizer(organizer);
        meeting.setTitle(request.title());
        meeting.setDescription(request.description());
        meeting.setStartAt(request.startAt());
        meeting.setEndAt(request.endAt());
    }
}
