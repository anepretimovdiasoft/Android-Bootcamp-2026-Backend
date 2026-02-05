package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.entity.Meetings;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingsRepository extends JpaRepository<Meetings, Long> {
    @Query("""
    select distinct m from Meetings m
    left join MeetingAttendees ma on ma.meeting = m
    where (m.creator.id = :userId or ma.user.id = :userId)
      and m.startTime >= :from
      and m.startTime < :to""")
    List<Meetings> findMyMeetingsByDay(
            Long userId,
            LocalDateTime from,
            LocalDateTime to
    );
}
