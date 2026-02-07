package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.domain.Invitation;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Page<Invitation> findByInviteeIdAndStatus(Long inviteeId, InvitationStatus status, Pageable pageable);
}
