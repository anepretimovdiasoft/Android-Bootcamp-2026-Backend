package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.ParticipantStatus;

public interface ParticipantStatusRepository extends JpaRepository<ParticipantStatus, Long> {
    ParticipantStatus getStatusById(Long id);
}
