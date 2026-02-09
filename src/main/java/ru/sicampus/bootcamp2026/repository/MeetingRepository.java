package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Optional<Meeting> findByTitle(String title);
    Page<Meeting> findByOrganizerId(Long organizerId, Pageable pageable);
    List<Meeting> findByDateAndStartHour(LocalDate date, int startHour);
}
