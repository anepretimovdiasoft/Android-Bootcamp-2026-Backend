package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.util.List;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByOrganizerId(Long organizerId);
}
