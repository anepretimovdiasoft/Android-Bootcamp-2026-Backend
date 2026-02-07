package ru.sicampus.bootcamp2026.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;
import ru.sicampus.bootcamp2026.domain.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query(value = """
        select m from Meeting m
        where m.startAt >= :start and m.startAt < :end
          and (
            m.organizer.id = :personId
            or exists (
              select 1 from Invitation i
              where i.meeting = m and i.invitee.id = :personId and i.status = :accepted
            )
          )
        order by m.startAt
        """,
        countQuery = """
        select count(m) from Meeting m
        where m.startAt >= :start and m.startAt < :end
          and (
            m.organizer.id = :personId
            or exists (
              select 1 from Invitation i
              where i.meeting = m and i.invitee.id = :personId and i.status = :accepted
            )
          )
        """)
    Page<Meeting> findUserMeetingsForDay(
        @Param("personId") Long personId,
        @Param("start") OffsetDateTime start,
        @Param("end") OffsetDateTime end,
        @Param("accepted") InvitationStatus accepted,
        Pageable pageable
    );

    @Query(value = """
        select date(m.start_at) as day, count(*) as count
        from meetings m
        where m.start_at >= :start and m.start_at < :end
          and (
            m.organizer_id = :personId
            or exists (
              select 1 from invitations i
              where i.meeting_id = m.id and i.invitee_id = :personId and i.status = 'ACCEPTED'
            )
          )
        group by date(m.start_at)
        order by date(m.start_at)
        """, nativeQuery = true)
    List<MeetingDayCountProjection> countUserMeetingsByDay(
        @Param("personId") Long personId,
        @Param("start") OffsetDateTime start,
        @Param("end") OffsetDateTime end
    );

    interface MeetingDayCountProjection {
        LocalDate getDay();
        long getCount();
    }
}
