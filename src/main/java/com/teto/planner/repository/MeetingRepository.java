package com.teto.planner.repository;

import com.teto.planner.entity.MeetingEntity;
import com.teto.planner.entity.MeetingStatus;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRepository extends JpaRepository<MeetingEntity, UUID> {

    @EntityGraph(attributePaths = {"organizer", "room", "participants", "participants.user"})
    @Query("""
        select distinct m from MeetingEntity m
        left join m.participants mp
        where m.meetingDate between :startDate and :endDate
          and (
            m.organizer.id = :userId
            or (
              mp.user.id = :userId
              and (mp.status = com.teto.planner.entity.ParticipantStatus.ACCEPTED
                   or (:includePending = true and mp.status = com.teto.planner.entity.ParticipantStatus.PENDING))
            )
          )
        """)
    Page<MeetingEntity> findForUserBetween(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("includePending") boolean includePending,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"organizer", "room", "participants", "participants.user"})
    Optional<MeetingEntity> findById(UUID id);

    boolean existsByRoom_IdAndMeetingDateAndStartHourAndStatus(
            UUID roomId,
            LocalDate meetingDate,
            Short startHour,
            MeetingStatus status
    );
}
