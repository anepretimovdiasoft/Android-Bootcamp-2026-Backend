package ru.sicampus.bootcamp2026.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entities.Invitation;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findAllByUserId(Long userId);
}
