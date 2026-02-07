package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.InvitedParticipant;

public interface InvitedParticipantRepository extends JpaRepository<InvitedParticipant, Long> {
}
