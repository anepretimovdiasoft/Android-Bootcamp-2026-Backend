package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.MeetingParticipant;
import ru.sicampus.bootcamp2026.model.MeetingParticipantId;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, MeetingParticipantId> {

    /**
     * Получение всех участников встречи
     */
    List<MeetingParticipant> findByMeetingIdId(UUID meetingId);

    /**
     * Получение участника встречи по ID встречи и пользователя
     */
    Optional<MeetingParticipant> findByMeetingIdIdAndUserIdId(UUID meetingId, UUID userId);

    /**
     * Получение всех приглашений пользователя со статусом PENDING
     */
    @Query("SELECT mp FROM MeetingParticipant mp " +
            "WHERE mp.userId.id = :userId AND mp.status = :status")
    List<MeetingParticipant> findByUserIdAndStatus(
            @Param("userId") UUID userId,
            @Param("status") ParticipantStatus status);

    /**
     * Получение всех приглашений пользователя
     */
    List<MeetingParticipant> findByUserIdId(UUID userId);

    /**
     * Удаление всех участников встречи
     */
    void deleteByMeetingIdId(UUID meetingId);
}
