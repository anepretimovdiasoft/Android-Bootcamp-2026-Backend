package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.Meeting;
import ru.sicampus.bootcamp2026.model.MeetingStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID> {

    /**
     * Получение всех встреч пользователя, как организатора
     */
    List<Meeting> findByOrganizerIdId(UUID organizerId);

    /**
     * Получение всех встреч пользователя в определённом статусе
     */
    @Query("SELECT DISTINCT m FROM Meeting m " +
            "JOIN m.meetingParticipants mp " +
            "WHERE mp.userId.id = :userId AND m.meetingStatus = :status")
    List<Meeting> findByUserIdAndStatus(
            @Param("userId") UUID userId,
            @Param("status") MeetingStatus status);

    /**
     * Получение всех встреч пользователя, как участника или организатора
     */
    @Query("SELECT DISTINCT m FROM Meeting m " +
            "LEFT JOIN m.meetingParticipants mp " +
            "WHERE m.organizer_id.id = :userId OR mp.userId.id = :userId")
    List<Meeting> findAllByUserId(@Param("userId") UUID userId);

    /**
     * Проверка наличия пересекающихся встреч у пользователя
     */
    @Query("SELECT COUNT(m) > 0 FROM Meeting m " +
            "JOIN m.meetingParticipants mp " +
            "WHERE mp.userId.id = :userId " +
            "AND m.meetingStatus = ru.sicampus.bootcamp2026.model.MeetingStatus.SCHEDULED" +
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
