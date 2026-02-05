package ru.sicampus.bootcamp2026.service;
import ru.sicampus.bootcamp2026.dto.*;
import java.time.LocalDate;
import java.util.List;

public interface MeetingService {
    void createMeeting(Long organizerId, MeetingCreateDTO dto);
    MeetingInfoDTO getMeetingInfo(Long meetingId);
    List<InvitationDTO> getInvitations(Long userId);
    void respondToInvitation(Long userId, Long invitationId, String status);
    List<ScheduleEntryDTO> getSchedule(Long userId, LocalDate start, LocalDate end);
}