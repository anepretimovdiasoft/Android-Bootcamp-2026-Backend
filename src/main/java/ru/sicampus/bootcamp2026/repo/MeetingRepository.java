package ru.sicampus.bootcamp2026.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.domain.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting getMeetingById(long id);
}