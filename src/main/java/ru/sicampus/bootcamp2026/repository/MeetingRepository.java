package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
