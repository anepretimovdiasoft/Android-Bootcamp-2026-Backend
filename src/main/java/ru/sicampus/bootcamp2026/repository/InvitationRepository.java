package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
