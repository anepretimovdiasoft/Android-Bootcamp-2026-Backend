package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Invitation;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @EntityGraph(attributePaths = {"employee", "meeting"})
    List<Invitation> findByEmployee_UsernameAndStatus(String username, String status);

    @EntityGraph(attributePaths = {"employee", "meeting"})
    boolean existsByMeeting_IdAndEmployee_Id(Long meetingId, Long employeeId);

    @EntityGraph(attributePaths = {"employee", "meeting"})
    boolean existsByMeeting_StartTimeAndEmployee_IdAndStatus(LocalDateTime startTime, Long employeeId, String status);
}