package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;
import ru.sicampus.bootcamp2026.exception.user.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.ScheduleService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Реализация сервиса расписания встреч.
 * <p>
 * Предоставляет методы для получения расписания пользователя на день, неделю, две недели или месяц.
 * В расписание включаются все встречи, где пользователь выступает либо организатором, либо приглашённым.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    /**
     * Возвращает расписание пользователя на указанную дату.
     * <p>
     * Учитываются только даты в пределах текущего календарного месяца.
     * Если передана дата вне текущего месяца — возвращается пустой список.
     * </p>
     *
     * @param userId идентификатор пользователя
     * @param date   дата, на которую запрашивается расписание
     * @return список встреч в указанный день, отсортированный по времени начала
     * @throws UserNotFoundException если пользователь с указанным ID не существует
     */
    @Override
    public List<MeetingDto> getDaySchedule(Long userId, LocalDate date) {
        if (!isInCurrentMonth(date)) {
            return List.of();
        }
        return getScheduleForRange(userId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
    /**
     * Возвращает расписание пользователя на текущую неделю.
     * <p>
     * Неделя всегда начинается с понедельника текущей календарной недели
     * (например, если сегодня суббота 6-го числа, а понедельник был 1-го,
     * то диапазон будет с 1-го по 7-е число).
     * </p>
     *
     * @param userId идентификатор пользователя
     * @return список встреч на текущую неделю, отсортированный по времени начала
     * @throws UserNotFoundException если пользователь с указанным ID не существует
     */
    @Override
    public List<MeetingDto> getWeekSchedule(Long userId) {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        return getScheduleForRange(
                userId,
                monday.atStartOfDay(),
                monday.plusWeeks(1).atStartOfDay()
        );
    }

    /**
     * Возвращает расписание пользователя на ближайшие две недели.
     * <p>
     * Диапазон начинается с понедельника текущей календарной недели и охватывает 14 дней.
     * Например, если сегодня суббота 6-го числа, а понедельник был 1-го,
     * то диапазон будет с 1-го по 14-е число.
     * </p>
     *
     * @param userId идентификатор пользователя
     * @return список встреч на две недели, отсортированный по времени начала
     * @throws UserNotFoundException если пользователь с указанным ID не существует
     */
    @Override
    public List<MeetingDto> getTwoWeeksSchedule(Long userId) {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        return getScheduleForRange(
                userId,
                monday.atStartOfDay(),
                monday.plusWeeks(2).atStartOfDay()
        );
    }

    /**
     * Возвращает расписание пользователя на текущий календарный месяц.
     * <p>
     * Диапазон включает все дни от 1-го числа до последнего дня текущего месяца.
     * </p>
     *
     * @param userId идентификатор пользователя
     * @return список встреч на текущий месяц, отсортированный по времени начала
     * @throws UserNotFoundException если пользователь с указанным ID не существует
     */
    @Override
    public List<MeetingDto> getMonthSchedule(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        return getScheduleForRange(
                userId,
                firstDay.atStartOfDay(),
                lastDay.plusDays(1).atStartOfDay()
        );
    }

    /**
     * Вспомогательный метод: получает расписание пользователя в заданном временном диапазоне.
     *
     * @param userId идентификатор пользователя
     * @param start  начало временного диапазона (включительно)
     * @param end    конец временного диапазона (исключительно)
     * @return список встреч в указанном диапазоне, отсортированный по времени начала
     * @throws UserNotFoundException если пользователь не найден
     */
    private List<MeetingDto> getScheduleForRange(Long userId, LocalDateTime start, LocalDateTime end) {
        validateUserExists(userId);
        return getMeetingsInTimeRange(userId, start, end);
    }

    /**
     * Вспомогательный метод: загружает все встречи пользователя в указанном временном интервале.
     * <p>
     * Включает:
     * <ul>
     *     <li>встречи, где пользователь — организатор;</li>
     *     <li>встречи, где пользователь — приглашённый (любой статус приглашения).</li>
     * </ul>
     * Дубликаты (если встреча попадает в оба списка) автоматически устраняются.
     * </p>
     *
     * @param userId идентификатор пользователя
     * @param start  начало временного диапазона
     * @param end    конец временного диапазона
     * @return список уникальных встреч, отсортированных по времени начала
     */
    private List<MeetingDto> getMeetingsInTimeRange(Long userId, LocalDateTime start, LocalDateTime end) {

        List<Meeting> asOrganizer = meetingRepository
                .findByOrganizerIdAndEndAtAfterAndStartAtBefore(userId, start, end);
        List<Invitation> acceptedInvitations = invitationRepository
                .findByInviteeIdAndStatus(userId, InvitationStatus.ACCEPTED);
        List<Meeting> asInvitee = acceptedInvitations.stream()
                .map(Invitation::getMeeting)
                .filter(meeting -> meeting.getEndAt().isAfter(start) && meeting.getStartAt().isBefore(end))
                .toList();

        Set<Meeting> allMeetings = new HashSet<>();
        allMeetings.addAll(asOrganizer);
        allMeetings.addAll(asInvitee);

        return allMeetings.stream()
                .map(MeetingMapper::toDto)
                .sorted(Comparator.comparing(MeetingDto::getStartAt))
                .toList();
    }

    /**
     * Проверяет существование пользователя в системе.
     *
     * @param userId идентификатор пользователя
     * @throws UserNotFoundException если пользователь не найден
     */

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Проверяет, принадлежит ли указанная дата текущему календарному месяцу.
     *
     * @param date дата для проверки
     * @return {@code true}, если дата в текущем месяце; иначе {@code false}
     */
    private boolean isInCurrentMonth(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.getYear() == now.getYear() && date.getMonth() == now.getMonth();
    }
}
