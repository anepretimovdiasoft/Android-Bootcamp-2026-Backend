package ru.sicampus.bootcamp2026.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.*;
import java.time.LocalDate;
import java.util.List;

public interface MeetingService {
    void createMeeting(Long organizerId, MeetingCreateDTO dto);
    MeetingInfoDTO getMeetingInfo(Long meetingId);
    Page<InvitationDTO> getInvitations(Long userId, Pageable pageable);
    List<InvitationDTO> getInvitations(Long userId);
    void respondToInvitation(Long userId, Long invitationId, String status);
    List<ScheduleEntryDTO> getSchedule(Long userId, LocalDate start, LocalDate end);
}