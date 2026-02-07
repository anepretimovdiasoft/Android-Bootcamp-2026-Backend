package com.teto.planner.repository;

import com.teto.planner.entity.RoomEntity;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    @Query("""
        select r from RoomEntity r
        where (:capacity is null or r.capacity >= :capacity)
          and not exists (
            select m.id from MeetingEntity m
            where m.room = r
              and m.meetingDate = :meetingDate
              and m.startHour = :startHour
              and m.status = com.teto.planner.entity.MeetingStatus.SCHEDULED
          )
        """)
    Page<RoomEntity> findAvailable(
            @Param("meetingDate") LocalDate meetingDate,
            @Param("startHour") Short startHour,
            @Param("capacity") Integer capacity,
            Pageable pageable
    );
}
