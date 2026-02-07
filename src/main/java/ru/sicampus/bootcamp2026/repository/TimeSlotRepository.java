package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query("""
            SELECT ts
            FROM TimeSlot ts
            WHERE ts.date.date = :date AND ts.meeting IS NOT NULL
            """)
    List<TimeSlot> getBookedSlotsByDate(@Param("date") LocalDate date);

    @Query("""
            SELECT COUNT(ts) > 0
            FROM TimeSlot ts
            WHERE ts.date.date = :date
            AND ts.startTime < :end
            AND ts.endTime > :start
            """)
    boolean existsAlreadyBookedSlots(
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end
    );
}
