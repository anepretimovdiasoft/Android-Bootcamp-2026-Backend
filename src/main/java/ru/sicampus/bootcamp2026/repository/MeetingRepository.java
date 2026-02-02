package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    boolean existsByOwner_UsernameAndStartTime(String username, LocalDateTime startTime);

    @EntityGraph(attributePaths = {"invitations", "invitations.employee"})
    List<Meeting> findByInvitations_Employee_UsernameAndInvitations_StatusAndStartTimeBetween(String username, String status, LocalDateTime startTime,
                                                                                 LocalDateTime endTime);
}