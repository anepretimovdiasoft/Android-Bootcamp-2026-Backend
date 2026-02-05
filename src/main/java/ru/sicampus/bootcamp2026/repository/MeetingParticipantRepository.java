package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.MeetingParticipant;
import java.util.List;
import java.util.Optional;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
    List<MeetingParticipant> findByUserIdAndInvitationStatus_StatusName(Long userId, String statusName);
    List<MeetingParticipant> findByMeetingId(Long meetingId);
    Optional<MeetingParticipant> findByMeetingIdAndUserId(Long meetingId, Long userId);
}