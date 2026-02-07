package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    // Проверка, что время встречи не пересекается с уже имеющимся
    @Query("SELECT COUNT(m) > 0 FROM Meeting m " +
            "LEFT JOIN Invitation i ON i.meeting = m AND i.user.id = :userId AND i.status = 'ACCEPTED' " +
            "WHERE (:date = m.date) AND " +
            "((m.organizer.id = :userId) OR (i.id IS NOT NULL)) AND " +
            "(m.timeStart < :endTime) AND (m.timeEnd > :startTime)")
    boolean existsTimeConflict(
            @Param("userId") long userId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query("SELECT DISTINCT m FROM Meeting m " +
            "LEFT JOIN Invitation i ON m = i.meeting " +
            "WHERE m.date = :date AND " +
            "(m.organizer.id = :userId OR (i.user.id = :userId AND i.status = 'ACCEPTED')) " +
            "ORDER BY m.timeStart")
    List<Meeting> daySchedule(
            @Param("userId") long userId,
            @Param("date") LocalDate date
    );

    @Query("SELECT DISTINCT m FROM Meeting m " +
            "LEFT JOIN FETCH m.organizer " +
            "LEFT JOIN FETCH Invitation i ON i.meeting = m AND i.user.id = :userId " +
            "WHERE m.date BETWEEN :startDate AND :endDate " +
            "AND (m.organizer.id = :userId OR (i.user.id = :userId AND i.status = 'ACCEPTED')) " +
            "ORDER BY m.date, m.timeStart")
    List<Meeting> schedule(
            @Param("userId") long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
