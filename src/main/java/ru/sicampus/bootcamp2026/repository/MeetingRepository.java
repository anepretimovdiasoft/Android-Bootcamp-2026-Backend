package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
//    Optional<Meeting> findByTimeSlotId(Long id);

    @Query("""
    SELECT DISTINCT m
    FROM Meeting m
    LEFT JOIN m.participants p
    JOIN m.timeSlot ts
    JOIN ts.date d
    WHERE (m.organizer.id = :userId OR p.participant.id = :userId)
    AND (:startDate IS NULL OR d.date >= :startDate)
    AND (:endDate IS NULL OR d.date <= :endDate)
    """)
    List<Meeting> findUserMeetings(
            @Param("userId") Long id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
    SELECT m
    FROM Meeting m
    JOIN m.participants p
    WHERE p.participant.id = :userId
    AND p.participantStatus.status = 'Ожидает'
    """)
    List<Meeting> findUserInvitations(@Param("userId") Long id);
}
