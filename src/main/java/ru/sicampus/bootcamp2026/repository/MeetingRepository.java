package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.Instant;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("""
            select distinct m
            from Meeting m
            left join m.invitations i
            where (m.organizer.id = :userId or (i.user.id = :userId and i.status = 'ACCEPTED'))
            and m.timeStart >= :from
            and m.timeEnd <= :to
            order by m.timeStart
            """)
    List<Meeting> findMeetingSchedule(
            @Param("userId") long userId,
            @Param("from") Instant from,
            @Param("to") Instant to
    );

    @Query("""
            select count(m) > 0
            from Meeting m
            where m.organizer.id = :userId
            and m.timeStart >= :from
            and m.timeEnd <= :to
            """)
    boolean existsOrganizerTimeConflict(
            @Param("userId") long userId,
            @Param("from") Instant from,
            @Param("to") Instant to
    );

    @Query("""
            select m
            from Meeting m
            where m.organizer.id = :userId
            and m.timeStart >= :from
            and m.timeEnd <= :to
            """)
    List<Meeting> findMeetingsByOrganizerTimeConflict(
            @Param("userId") long userId,
            @Param("from") Instant from,
            @Param("to") Instant to
    );
}
