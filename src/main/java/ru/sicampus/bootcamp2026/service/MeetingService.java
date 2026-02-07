package ru.sicampus.bootcamp2026.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.sicampus.bootcamp2026.api.dto.MeetingDayCountResponse;
import ru.sicampus.bootcamp2026.api.dto.MeetingRequest;
import ru.sicampus.bootcamp2026.api.error.BadRequestException;
import ru.sicampus.bootcamp2026.api.error.NotFoundException;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    public MeetingService(MeetingRepository meetingRepository, PersonRepository personRepository) {
        this.meetingRepository = meetingRepository;
        this.personRepository = personRepository;
    }

    public Page<Meeting> list(Pageable pageable) {
        return meetingRepository.findAll(pageable);
    }

    public Page<Meeting> listForUserDay(Long personId, LocalDate date, Pageable pageable) {
        OffsetDateTime start = date.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = start.plusDays(1);
        return meetingRepository.findUserMeetingsForDay(
            personId,
            start,
            end,
            InvitationStatus.ACCEPTED,
            pageable
        );
    }

    public List<MeetingDayCountResponse> countForUserMonth(Long personId, YearMonth month) {
        OffsetDateTime start = month.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = start.plusMonths(1);

        Map<LocalDate, Long> counts = new HashMap<>();
        for (MeetingRepository.MeetingDayCountProjection row :
            meetingRepository.countUserMeetingsByDay(personId, start, end)
        ) {
            counts.put(row.getDay(), row.getCount());
        }

        List<MeetingDayCountResponse> result = new ArrayList<>();
        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            LocalDate date = month.atDay(day);
            long count = counts.getOrDefault(date, 0L);
            result.add(new MeetingDayCountResponse(date, count));
        }
        return result;
    }

    public List<MeetingDayCountResponse> countForUserWeek(Long personId, LocalDate startDate) {
        OffsetDateTime start = startDate.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = start.plusDays(7);

        Map<LocalDate, Long> counts = new HashMap<>();
        for (MeetingRepository.MeetingDayCountProjection row :
            meetingRepository.countUserMeetingsByDay(personId, start, end)
        ) {
            counts.put(row.getDay(), row.getCount());
        }

        List<MeetingDayCountResponse> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            long count = counts.getOrDefault(date, 0L);
            result.add(new MeetingDayCountResponse(date, count));
        }
        return result;
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
