package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.MeetingParticipant;
import java.util.List;
import java.util.Optional;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
    List<MeetingParticipant> findByUserIdAndInvitationStatus_StatusName(Long userId, String statusName);
    List<MeetingParticipant> findByMeetingId(Long meetingId);
    Page<MeetingParticipant> findByUserIdAndInvitationStatus_StatusName(Long userId, String statusName, Pageable pageable);
    Optional<MeetingParticipant> findByMeetingIdAndUserId(Long meetingId, Long userId);
}