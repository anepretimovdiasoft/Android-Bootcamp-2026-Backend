package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Optional<Meeting> findByTitle(String title);
}
