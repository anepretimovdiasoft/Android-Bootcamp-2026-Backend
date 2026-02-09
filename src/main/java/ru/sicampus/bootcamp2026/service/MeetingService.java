package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(MeetingCreateDTO meetingCreateDTO, String username);
    MeetingDTO getMeetingByID(Long id);
    List<MeetingDTO> getSchedule(LocalDateTime start, LocalDateTime end, String username);
    List<InvitationEmployeeDTO> getEmployeesByMeetingID(Long Id);
    void deleteById(Long id, String username);
}
