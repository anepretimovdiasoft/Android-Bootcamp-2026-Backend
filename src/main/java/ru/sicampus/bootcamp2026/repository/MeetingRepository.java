package ru.sicampus.bootcamp2026.repository;

import ru.sicampus.bootcamp2026.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
