package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
