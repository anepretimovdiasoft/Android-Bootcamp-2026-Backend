package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingParticipantDTO;
import java.util.List;

public interface MeetingParticipantService {
    List<MeetingParticipantDTO> getParticipantsByMeeting(Long meetingId);
    List<MeetingParticipantDTO> getParticipantsByUser(Long userId);
    MeetingParticipantDTO addParticipant(Long meetingId, MeetingParticipantDTO dto);
    void updateParticipantStatus(Long meetingId, Long userId, MeetingParticipantDTO dto);
    void removeParticipant(Long meetingId, Long userId);

    Page<MeetingParticipantDTO> getAllMeetingsParticipantPaginated(Pageable pageable);
}