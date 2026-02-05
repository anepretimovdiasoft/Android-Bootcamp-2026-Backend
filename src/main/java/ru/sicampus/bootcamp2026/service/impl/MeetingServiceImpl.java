package ru.sicampus.bootcamp2026.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dto.CreateMeetingDto;
import ru.sicampus.bootcamp2026.dto.MeetingsDto;
import ru.sicampus.bootcamp2026.exception.InvalidMeetingTimeException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendees;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendeesId;
import ru.sicampus.bootcamp2026.model.entity.Meetings;
import ru.sicampus.bootcamp2026.model.entity.Users;
import ru.sicampus.bootcamp2026.model.enums.UserStatus;
import ru.sicampus.bootcamp2026.repository.MeetingAttendeesRepository;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingsRepository meetingRepository;

    private final UserRepository userRepository;

    private final MeetingAttendeesRepository meetingAttendeesRepository;

    @Override
    public MeetingsDto getMeeting(Long id) {
        return meetingRepository.findById(id)
                .map(MeetingMapper::toDto)
                .orElseThrow(() -> new MeetingNotFoundException(id));
    }

    @Transactional
    @Override
    public void createMeeting(CreateMeetingDto request) {
        if (request.getCreatorId() == null) {
            throw new InvalidMeetingTimeException("creatorId must not be null");
        }
        if (request.getAttendeeIds() == null || request.getAttendeeIds().isEmpty()) {
            throw new InvalidMeetingTimeException("attendeeIds must not be empty");
        }
        if (request.getAttendeeIds().contains(null)) {
            throw new InvalidMeetingTimeException("attendeeIds contains null value");
        }
        Users creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new UserNotFoundException(request.getCreatorId()));
        LocalDateTime start = request.getStartTime();
        LocalDateTime end = request.getEndTime();

        validateMeetingTime(start, end);

        Meetings meeting = new Meetings();
        meeting.setCreator(creator);
        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setStartTime(request.getStartTime());
        meeting.setEndTime(request.getEndTime());
        meeting.setLocation(request.getLocation());
        meeting.setStatus(request.getStatus());

        meeting = meetingRepository.saveAndFlush(meeting);
        for (Long userId : request.getAttendeeIds()) {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            MeetingAttendees attendee = new MeetingAttendees();
            attendee.setId(new MeetingAttendeesId(
                    meeting.getId(),
                    user.getId()
            ));
            attendee.setMeeting(meeting);
            attendee.setUser(user);
            attendee.setStatus(UserStatus.PENDING);
            meetingAttendeesRepository.save(attendee);
        }

    }

    @Override
    public List<MeetingsDto> getMyMeetingsByDay(Long userId, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return meetingRepository
                .findMyMeetingsByDay(userId, from, to)
                .stream()
                .map(MeetingMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void deleteMeeting(long id) {
        Meetings meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));
        meetingAttendeesRepository.deleteAll(meeting.getAttendees());
        meetingRepository.delete(meeting);
    }

    private void validateMeetingTime(LocalDateTime start, LocalDateTime end) {
        if (start.getMinute() != 0 || end.getMinute() != 0) {
            throw new InvalidMeetingTimeException("Meeting must start and end at full hour (minutes = 00)");
        }

        if (!end.isAfter(start) || end.isAfter(start.plusHours(1))) {
            throw new InvalidMeetingTimeException("Meeting duration must be maximum 1 hour");
        }
    }

}
