package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meetings;

@Repository
public interface MeetingsRepository extends JpaRepository<Meetings, Long> {
}
