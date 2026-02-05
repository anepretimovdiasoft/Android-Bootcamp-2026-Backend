package ru.sicampus.bootcamp2026.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingsDto;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendees;
import ru.sicampus.bootcamp2026.dto.MeetingAttendeeDto;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendeesId;
import ru.sicampus.bootcamp2026.model.entity.Meetings;
import ru.sicampus.bootcamp2026.model.entity.Users;
import ru.sicampus.bootcamp2026.model.enums.UserStatus;
import ru.sicampus.bootcamp2026.repository.MeetingAttendeesRepository;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingAttendeesService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;


import java.util.List;


@RequiredArgsConstructor
@Service
public class MeetingAttendeesServiceImpl implements MeetingAttendeesService {
    private final MeetingsRepository meetingsRepository;
    private final MeetingAttendeesRepository meetingAttendeesRepository;
    private final UserRepository userRepository;

    @Override
    public List<MeetingAttendeeDto> getAttendeesByMeetingId(Long meetingId) {
        Meetings meeting = meetingsRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException(meetingId));
        return meeting.getAttendees()
                .stream()
                .map(att -> new MeetingAttendeeDto(
                        att.getUser().getId(),
                        att.getUser().getUsername(),
                        att.getStatus()
                )).toList();
    }

    @Override
    public void respondToInvitation(Long meetingId, Long userId, UserStatus status) {
        MeetingAttendees attendee = meetingAttendeesRepository.findById(
                new MeetingAttendeesId(meetingId, userId))
                .orElseThrow(() -> new InvitationNotFoundException(
                        "Invitation for meeting " + meetingId + " and user " + userId + " not found"
                ));
        attendee.setStatus(status);
        meetingAttendeesRepository.save(attendee);
    }

    @Override
    public List<MeetingsDto> getMeetingsForUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<MeetingAttendees> invitations = meetingAttendeesRepository.findAllByUser(user);
        return invitations.stream()
                .map(att -> MeetingMapper.toDto(att.getMeeting()))
                .toList();
    }

}
