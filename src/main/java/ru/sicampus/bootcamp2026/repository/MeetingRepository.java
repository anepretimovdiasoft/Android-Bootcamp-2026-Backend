package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Users;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query(value = """
            SELECT DISTINCT m.id
            FROM meeting m
            LEFT JOIN users_meeting um ON m.id = um.meeting
            WHERE (um.member = :userId OR m.creator = :userId)
            AND (
                (m.start < :endTime)
                AND
                (DATEADD('MINUTE', m.duration, m.start) > :startTime)
            )
    """, nativeQuery = true)
    List<Long> findOverlappingMeetingIds(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("userId") Long userId);

    @Query(value = """
        SELECT DISTINCT m.* FROM meeting m
        LEFT JOIN users_meeting um ON m.id = um.meeting AND um.member = :userId
        WHERE DATEADD('MINUTE', m.duration, m.start) > NOW()
          AND (m.creator = :userId OR um.member IS NOT NULL)
        ORDER BY m.start ASC
    """, nativeQuery = true)
    List<Meeting> findAllAfterNow(@Param("userId") Long userId);
}
