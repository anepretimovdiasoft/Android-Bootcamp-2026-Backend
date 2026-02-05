package ru.sicampus.bootcamp2026.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entities.Meeting;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findAllByOrganizerId(Long organizerId);
}
