package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(MeetingCreateDTO meetingCreateDTO);
    MeetingDTO getMeetingByID(Long Id);
    List<MeetingDTO> getSchedule(LocalDateTime start, LocalDateTime end);
    List<InvitationEmployeeDTO> getEmployeesByMeetingID(Long Id);
}
