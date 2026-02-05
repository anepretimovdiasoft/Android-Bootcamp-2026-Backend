package ru.sicampus.bootcamp2026.repository;

import ru.sicampus.bootcamp2026.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
