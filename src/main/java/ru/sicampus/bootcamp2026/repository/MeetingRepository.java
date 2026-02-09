package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("""
            SELECT DISTINCT m
            FROM Meeting m
            LEFT JOIN FETCH m.participants p
            JOIN FETCH m.timeSlot ts
            JOIN FETCH ts.date d
            JOIN FETCH m.organizer
            LEFT JOIN FETCH p.participant
            LEFT JOIN FETCH p.participantStatus
            WHERE (m.organizer.id = :userId OR p.participant.id = :userId)
            AND (:startDate IS NULL OR d.date >= :startDate)
            AND (:endDate IS NULL OR d.date <= :endDate)
            """)
    List<Meeting> findUserMeetings(
            @Param("userId") Long id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Sort sort
    );

    @Query("""
            SELECT DISTINCT m
            FROM Meeting m
            LEFT JOIN FETCH m.participants p
            JOIN FETCH m.timeSlot ts
            JOIN FETCH ts.date d
            JOIN FETCH m.organizer
            LEFT JOIN FETCH p.participant
            LEFT JOIN FETCH p.participantStatus
            WHERE (m.organizer.id = :organizerId)
            """)
    List<Meeting> findOrganizerMeetings(@Param("organizerId") Long id);

    @Query("""
            SELECT DISTINCT m
            FROM Meeting m
            JOIN FETCH m.organizer
            JOIN FETCH m.timeSlot ts
            JOIN FETCH ts.date
            JOIN FETCH m.participants p
            JOIN FETCH p.participant
            JOIN FETCH p.participantStatus
            WHERE p.participant.id = :userId
            AND p.participantStatus.status = 'Ожидает'
            """)
    List<Meeting> findUserInvitations(@Param("userId") Long id);


    @Override
    @EntityGraph(attributePaths = {"organizer", "timeSlot", "participants", "participants.participant"})
    List<Meeting> findAll();
}
