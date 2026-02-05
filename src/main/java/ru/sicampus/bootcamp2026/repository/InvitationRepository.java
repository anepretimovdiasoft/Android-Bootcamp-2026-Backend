package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByMeetingId(Long id);
    void deleteByMeetingId(Long meetingId);
    List<Invitation> findByInviteeId(Long inviteeId);
    List<Invitation> findByInviteeIdAndStatus(
            Long inviteeId,
            InvitationStatus status
    );
}
