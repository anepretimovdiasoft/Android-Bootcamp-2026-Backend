package ru.sicampus.bootcamp2026.service;


import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingAttendeeDto;
import ru.sicampus.bootcamp2026.dto.MeetingsDto;
import ru.sicampus.bootcamp2026.model.enums.UserStatus;

import java.util.List;

@Service
public interface MeetingAttendeesService {
    List<MeetingAttendeeDto> getAttendeesByMeetingId(Long meetingId);

    void respondToInvitation(Long meetingId, Long userId, UserStatus status);

    List<MeetingsDto> getMeetingsForUser(Long userId);
}
