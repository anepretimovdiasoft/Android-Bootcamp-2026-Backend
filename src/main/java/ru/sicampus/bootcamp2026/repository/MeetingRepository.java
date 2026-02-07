package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностями {@link Meeting}.
 * <p>
 * Предоставляет методы для поиска встреч по организатору или приглашённому пользователю.
 * Все методы используют стандартные возможности Spring Data JPA (method query derivation).
 * </p>
 */
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    /**
     * Находит все встречи, созданные указанным организатором, которые пересекаются
     * с заданным временным интервалом {@code [start, end)}.
     * <p>
     * Встреча считается пересекающейся, если выполняется условие:
     * {@code meeting.endAt > start && meeting.startAt < end}.
     * Это означает, что даже частичное пересечение (например, встреча 10:00–12:00
     * при запросе 11:00–13:00) будет включено в результат.
     * </p>
     *
     * @param organizerId идентификатор организатора
     * @param start       начало временного интервала (используется в условии {@code endAt > start})
     * @param end         конец временного интервала (используется в условии {@code startAt < end})
     * @return список встреч, удовлетворяющих условиям; пустой список, если совпадений нет
     */
    List<Meeting> findByOrganizerIdAndEndAtAfterAndStartAtBefore(
            Long organizerId,
            LocalDateTime start,
            LocalDateTime end
    );

    /**
     * Находит все встречи, в которых указанный пользователь участвует как приглашённый.
     * <p>
     * Метод выполняет JOIN по связи {@code Meeting.invitations}, где
     * {@code Invitation.invitee.id = inviteeId}.
     * </p>
     * <p>
     * Метод возвращает <b>все</b> встречи, независимо от статуса приглашения
     * (PENDING, ACCEPTED, DECLINED) и времени проведения. Для фильтрации по статусу
     * или дате требуется дополнительная обработка в сервисном слое.
     * </p>
     *
     * @param inviteeId идентификатор приглашённого пользователя
     * @return список встреч, в которых пользователь является приглашённым;
     *         пустой список, если таких встреч нет
     */
    List<Meeting> findByInvitationsInviteeId(Long inviteeId);
}
