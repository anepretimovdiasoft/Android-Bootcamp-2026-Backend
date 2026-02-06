package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.Meeting;
import ru.sicampus.bootcamp2026.model.MeetingStatus;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID> {

    /**
     * Получение всех встреч пользователя в определённом статусе (с пагинацией).
     */
    @Query(
            value = "SELECT DISTINCT m FROM Meeting m " +
                    "JOIN m.meetingParticipants mp " +
                    "WHERE mp.userId.id = :userId AND m.meetingStatus = :status",
            countQuery = "SELECT COUNT(DISTINCT m.id) FROM Meeting m " +
                    "JOIN m.meetingParticipants mp " +
                    "WHERE mp.userId.id = :userId AND m.meetingStatus = :status"
    )
    Page<Meeting> findByUserIdAndStatus(
            @Param("userId") UUID userId,
            @Param("status") MeetingStatus status,
            Pageable pageable
    );

    /**
     * Получение всех встреч пользователя, как участника или организатора (с пагинацией).
     */
    @Query(
            value = "SELECT DISTINCT m FROM Meeting m " +
                    "LEFT JOIN m.meetingParticipants mp " +
                    "WHERE m.organizer_id.id = :userId OR mp.userId.id = :userId",
            countQuery = "SELECT COUNT(DISTINCT m.id) FROM Meeting m " +
                    "LEFT JOIN m.meetingParticipants mp " +
                    "WHERE m.organizer_id.id = :userId OR mp.userId.id = :userId"
    )
    Page<Meeting> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

    /**
     * Проверка наличия пересекающихся встреч у пользователя
     */
    @Query("SELECT COUNT(m) > 0 FROM Meeting m " +
            "JOIN m.meetingParticipants mp " +
            "WHERE mp.userId.id = :userId " +
            "AND m.meetingStatus = ru.sicampus.bootcamp2026.model.MeetingStatus.SCHEDULED " +
            "AND m.endTime > :startTime " +
            "AND m.startTime < :endTime")
    boolean existsByUserIdAndTimeOverlap(
            @Param("userId") UUID userId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);

    /**
     * Проверка наличия пересекающихся встреч у организатора
     */
    @Query("SELECT COUNT(m) > 0 FROM Meeting m " +
            "WHERE m.organizer_id.id = :organizerId " +
            "AND m.meetingStatus = ru.sicampus.bootcamp2026.model.MeetingStatus.SCHEDULED " +
            "AND m.endTime > :startTime " +
            "AND m.startTime < :endTime")
    boolean existsByOrganizerIdAndTimeOverlap(
            @Param("organizerId") UUID organizerId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);
}
