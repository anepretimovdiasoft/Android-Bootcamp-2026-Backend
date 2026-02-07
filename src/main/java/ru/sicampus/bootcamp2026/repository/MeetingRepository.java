package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Override
    Page<Meeting> findAll(Pageable pageable);

    List<Meeting> findByOrganizerId(Long organizerId);
    List<Meeting> findByTitleContainingIgnoreCase(String title);
}