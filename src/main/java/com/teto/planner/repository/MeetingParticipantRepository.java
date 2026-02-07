package com.teto.planner.repository;

import com.teto.planner.entity.MeetingParticipantEntity;
import com.teto.planner.entity.MeetingParticipantId;
import com.teto.planner.entity.ParticipantStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipantEntity, MeetingParticipantId> {

    @Query("""
        select count(mp) from MeetingParticipantEntity mp
        join mp.meeting m
        where mp.user.id = :userId
          and mp.meetingDate = :meetingDate
          and mp.startHour = :startHour
          and mp.status = com.teto.planner.entity.ParticipantStatus.ACCEPTED
          and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
        """)
    long countAcceptedAtSlot(
            @Param("userId") UUID userId,
            @Param("meetingDate") LocalDate meetingDate,
            @Param("startHour") Short startHour
    );

    @Query("""
        select mp from MeetingParticipantEntity mp
        join fetch mp.meeting m
        where mp.user.id = :userId
          and mp.meetingDate = :meetingDate
          and mp.status = com.teto.planner.entity.ParticipantStatus.ACCEPTED
          and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
        """)
    List<MeetingParticipantEntity> findBusySlots(
            @Param("userId") UUID userId,
            @Param("meetingDate") LocalDate meetingDate
    );

    @Query("""
        select mp.user.id as userId, coalesce(sum(m.durationHours), 0) as hours
        from MeetingParticipantEntity mp
        join mp.meeting m
        where mp.user.id in :userIds
          and mp.meetingDate = :meetingDate
          and mp.status = com.teto.planner.entity.ParticipantStatus.ACCEPTED
          and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
        group by mp.user.id
        """)
    List<BusyHoursProjection> sumBusyHours(
            @Param("userIds") List<UUID> userIds,
            @Param("meetingDate") LocalDate meetingDate
    );

    @EntityGraph(attributePaths = {"meeting", "meeting.organizer", "meeting.room", "meeting.participants", "meeting.participants.user", "user"})
    @Query("""
        select mp from MeetingParticipantEntity mp
        join mp.meeting m
        where mp.user.id = :userId
          and mp.status = :status
          and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
        """)
    Page<MeetingParticipantEntity> findByUserIdAndStatus(
            @Param("userId") UUID userId,
            @Param("status") ParticipantStatus status,
            Pageable pageable
    );

    @Query("""
        select mp from MeetingParticipantEntity mp
        join mp.meeting m
        where mp.user.id = :userId
          and mp.meetingDate = :meetingDate
          and mp.startHour = :startHour
          and mp.status = com.teto.planner.entity.ParticipantStatus.PENDING
          and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
        """)
    List<MeetingParticipantEntity> findPendingAtSlot(
            @Param("userId") UUID userId,
            @Param("meetingDate") LocalDate meetingDate,
            @Param("startHour") Short startHour
    );

    @Query("""
        select mp from MeetingParticipantEntity mp
        join fetch mp.user u
        join mp.meeting m
        where mp.user.id in :userIds
          and mp.meetingDate = :meetingDate
          and mp.status = com.teto.planner.entity.ParticipantStatus.ACCEPTED
          and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
        """)
    List<MeetingParticipantEntity> findAcceptedForUsersOnDate(
            @Param("userIds") List<UUID> userIds,
            @Param("meetingDate") LocalDate meetingDate
    );

}
