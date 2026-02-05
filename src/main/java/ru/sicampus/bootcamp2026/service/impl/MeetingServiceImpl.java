package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;
import ru.sicampus.bootcamp2026.exception.meeting.*;
import ru.sicampus.bootcamp2026.exception.user.OrganizerNotExistsException;
import ru.sicampus.bootcamp2026.exception.user.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    private static final Long DEMO_ORGANIZER_ID = 1L;
    private static final Long UNREACHABLE_MEETING_ID = -1L;

    /**
     * Возвращает информацию о встрече по её идентификатору.
     *
     * @param id идентификатор встречи
     * @return данные встречи в виде DTO
     * @throws MeetingNotFoundException если встреча с указанным ID не найдена
     */
    @Override
    public MeetingDto getMeetingById(Long id) {
        return meetingRepository.findById(id)
                .map(MeetingMapper::toDto)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
    }

    /**
     * Создаёт новую встречу.
     * <p>
     * Требования:
     * <ul>
     *   <li>Время начала и окончания должно быть кратно часу (HH:00)</li>
     *   <li>Организатор (демо-пользователь) не должен быть занят в этот слот</li>
     *   <li>Ни один из приглашённых не должен быть занят в этот слот</li>
     *   <li>Организатор не может быть в списке приглашённых</li>
     * </ul>
     * </p>
     *
     * @param dto данные новой встречи
     * @return созданная встреча в виде DTO
     * @throws TimeIsOnHourException если время не кратно часу
     * @throws EndBeforeStartMeetingException если время окончания <= времени начала
     * @throws OrganizerBusyInThisTimeException если организатор занят
     * @throws UserBusyInThisTimeException если один из приглашённых занят
     * @throws OrganizerInInviteesException если организатор указан среди приглашённых
     */
    @Override
    public MeetingDto createMeeting(MeetingDto dto) {
        validateMeetingTime(dto);
        var meeting = createMeetingFromDto(new Meeting(), dto);

        checkBusyTimeForOrganizer(dto, UNREACHABLE_MEETING_ID);

        var users = resolveInvitees(dto.getInviteeLogins());
        var lengthListBefore = users.size();
        users.removeIf(user -> user.getId().equals(DEMO_ORGANIZER_ID));
        var lengthListAfter = users.size();
        if (lengthListBefore != lengthListAfter){
            throw new OrganizerInInviteesException("Organizer won't be in list invitees");
        }
        for (var user : users){
            if (hasConflict(user.getId(), dto.getStartAt(), dto.getEndAt(), UNREACHABLE_MEETING_ID)){
                throw new UserBusyInThisTimeException("User: "+ user.getLogin() + " busy in this time. Delete this user from list invitees and try again");
            }
        }
        meetingRepository.save(meeting);

        for (var user : users){
            Invitation invitation = new Invitation();
            invitation.setMeeting(meeting);
            invitation.setInvitee(user);
            invitation.setStatus(InvitationStatus.PENDING);

            invitationRepository.save(invitation);
        }
        return MeetingMapper.toDto(meeting);
    }

    /**
     * Обновляет существующую встречу.
     * <p>
     * Логика обновления зависит от типа изменений:
     * <ul>
     *   <li><b>Существенные изменения</b> (время или место): все приглашения удаляются,
     *       создаются новые со статусом PENDING. Участники должны подтвердить участие заново.</li>
     *   <li><b>Несущественные изменения</b> (состав участников, описание, URL и т.д.):
     …     * @throws TimeIsOnHourException если время не кратно часу
     * @throws EndBeforeStartMeetingException если время окончания <= времени начала
     * @throws OrganizerBusyInThisTimeException если организатор занят в новом слоте
     * @throws UserBusyInThisTimeException если новый участник занят
     */
    @Override
    public MeetingDto updateMeeting(Long id, MeetingDto dto) {
        validateMeetingTime(dto);
        var existingMeeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));

        checkBusyTimeForOrganizer(dto, id);

        boolean locationChanged = !Objects.equals(existingMeeting.getLocation(), dto.getLocation());
        boolean timeChanged = !existingMeeting.getStartAt().equals(dto.getStartAt())
                || !existingMeeting.getEndAt().equals(dto.getEndAt());
        boolean isSignificantChange = timeChanged || locationChanged;

        createMeetingFromDto(existingMeeting, dto);

        List<String> newLogins = Optional.ofNullable(dto.getInviteeLogins()).orElse(List.of());

        if (isSignificantChange) {
            validateNewInviteesForConflicts(newLogins, existingMeeting);
        } else {
            validateAddedInviteesForConflicts(newLogins, existingMeeting);
        }

        var savedMeeting = meetingRepository.save(existingMeeting);

        if (isSignificantChange) {
            recreateAllInvitations(savedMeeting, newLogins);
        } else {
            updateInviteesOnly(savedMeeting, newLogins);
        }

        return MeetingMapper.toDto(savedMeeting);
    }

    /**
     * Проверяет конфликты для всех новых приглашённых (используется при существенных изменениях).
     *
     * @param newLogins логины новых приглашённых
     * @param meeting   встреча (с новыми временем/местом)
     * @throws UserBusyInThisTimeException если один из приглашённых занят
     */
    private void validateNewInviteesForConflicts(List<String> newLogins, Meeting meeting) {
        var newInvitees = resolveInvitees(newLogins);
        newInvitees.removeIf(user -> user.getId().equals(DEMO_ORGANIZER_ID));
        for (var invitee : newInvitees) {
            if (hasConflict(invitee.getId(), meeting.getStartAt(), meeting.getEndAt(), meeting.getId())) {
                throw new UserBusyInThisTimeException("User: " + invitee.getLogin() + " busy in this time...");
            }
        }
    }

    /**
     * Проверяет конфликты только для добавленных приглашённых (используется при несущественных изменениях).
     *
     * @param newLogins логины новых приглашённых
     * @param meeting   встреча
     * @throws UserBusyInThisTimeException если один из добавленных приглашённых занят
     */
    private void validateAddedInviteesForConflicts(List<String> newLogins, Meeting meeting) {
        List<Invitation> currentInvitations = invitationRepository.findByMeetingId(meeting.getId());
        Set<String> currentLogins = currentInvitations.stream()
                .map(inv -> inv.getInvitee().getLogin())
                .collect(Collectors.toSet());

        Set<String> toAdd = new HashSet<>(newLogins);
        toAdd.removeAll(currentLogins);

        if (!toAdd.isEmpty()) {
            var newUsers = resolveInvitees(new ArrayList<>(toAdd));
            newUsers.removeIf(user -> user.getId().equals(DEMO_ORGANIZER_ID));
            for (var user : newUsers) {
                if (hasConflict(user.getId(), meeting.getStartAt(), meeting.getEndAt(), meeting.getId())) {
                    throw new UserBusyInThisTimeException("User: " + user.getLogin() + " busy in this time...");
                }
            }
        }
    }

    /**
     * Полностью пересоздаёт приглашения для встречи (используется при существенных изменениях).
     * Все старые приглашения удаляются, новые создаются со статусом PENDING.
     *
     * @param meeting    встреча
     * @param newLogins  логины приглашённых
     */
    private void recreateAllInvitations(Meeting meeting, List<String> newLogins) {
        invitationRepository.deleteByMeetingId(meeting.getId());
        var newInvitees = resolveInvitees(newLogins);
        newInvitees.removeIf(user -> user.getId().equals(DEMO_ORGANIZER_ID));
        for (var invitee : newInvitees) {
            Invitation inv = new Invitation();
            inv.setMeeting(meeting);
            inv.setInvitee(invitee);
            inv.setStatus(InvitationStatus.PENDING);
            invitationRepository.save(inv);
        }
    }

    /**
     * Удаляет встречу по идентификатору.
     * <p>
     * Безопасность (проверка прав) будет реализована позже через Spring Security.
     * </p>
     *
     * @param id идентификатор встречи
     */
    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }

    /**
     * Заполняет сущность встречи данными из DTO.
     * Организатор всегда устанавливается как демо-пользователь (ID = 1).
     *
     * @param meeting целевая сущность встречи
     * @param dto     источник данных
     * @return заполненная сущность встречи
     * @throws OrganizerNotExistsException если демо-организатор не найден в БД
     */
    private Meeting createMeetingFromDto(Meeting meeting, MeetingDto dto){
        var organizer = userRepository.findById(DEMO_ORGANIZER_ID)
                .orElseThrow(() -> new OrganizerNotExistsException("Meeting can't be created because organizer doesn't exist"));

        meeting.setTitle(dto.getTitle());
        meeting.setOrganizer(organizer);
        meeting.setStartAt(dto.getStartAt());
        meeting.setEndAt(dto.getEndAt());
        meeting.setDescription(dto.getDescription());
        meeting.setLocation(dto.getLocation());
        meeting.setType(dto.getType());
        meeting.setUrl(dto.getUrl());

        return meeting;
    }

    /**
     * Проверяет, что переданное время кратно полному часу (минуты, секунды и наносекунды = 0).
     *
     * @param time проверяемое время
     * @return true, если время НЕ кратно часу
     */
    private boolean isNotOnHour(LocalDateTime time) {
        return time.getMinute() != 0 || time.getSecond() != 0 || time.getNano() != 0;
    }

    /**
     * Валидирует временные параметры встречи.
     *
     * @param dto данные встречи
     * @throws TimeIsOnHourException если время не кратно часу
     * @throws EndBeforeStartMeetingException если время окончания <= времени начала
     */
    private void validateMeetingTime(MeetingDto dto) {
        if (isNotOnHour(dto.getStartAt()) || isNotOnHour(dto.getEndAt())) {
            throw new TimeIsOnHourException("Time must be on the hour (e.g., 14:00, not 14:03)");
        }
        if (!dto.getStartAt().isBefore(dto.getEndAt())){
            throw new EndBeforeStartMeetingException("Start time must be before end time");
        }
    }

    /**
     * Преобразует список логинов в список пользователей.
     * Проверяет, что все указанные пользователи существуют.
     *
     * @param logins список логинов
     * @return список соответствующих пользователей
     * @throws UserNotFoundException если хотя бы один пользователь не найден
     */
    private List<User> resolveInvitees(List<String> logins) {
        if (logins == null || logins.isEmpty()) return List.of();

        var users = userRepository.findByLoginIn(logins);
        if (users.size() != logins.size()) {
            throw new UserNotFoundException("One or more invitees not found");
        }
        return users;
    }

    /**
     * Проверяет, есть ли у пользователя конфликт занятости в указанный временной интервал.
     *
     * @param userId           идентификатор пользователя
     * @param start            начало проверяемого интервала
     * @param end              конец проверяемого интервала
     * @param excludeMeetingId идентификатор встречи, которую следует игнорировать (например, при обновлении)
     * @return true, если найден конфликт
     */
    private boolean hasConflict(Long userId, LocalDateTime start, LocalDateTime end, Long excludeMeetingId) {
        List<Meeting> asOrganizer = meetingRepository
                .findByOrganizerIdAndEndAtAfterAndStartAtBefore(userId, start, end);
        boolean conflictAsOrganizer = asOrganizer.stream()
                .anyMatch(m -> !m.getId().equals(excludeMeetingId));

        if (conflictAsOrganizer) return true;

        List<Meeting> asInvitee = meetingRepository.findByInvitationsInviteeId(userId);
        return asInvitee.stream()
                .anyMatch(m -> !m.getId().equals(excludeMeetingId)
                        && m.getEndAt().isAfter(start)
                        && m.getStartAt().isBefore(end));
    }

    /**
     * Проверяет, занят ли демо-организатор в указанный временной интервал.
     *
     * @param dto               данные встречи
     * @param excludeMeetingId  идентификатор встречи, которую следует игнорировать
     * @throws OrganizerBusyInThisTimeException если организатор занят
     */
    private void checkBusyTimeForOrganizer(MeetingDto dto, Long excludeMeetingId){
        if (hasConflict(DEMO_ORGANIZER_ID, dto.getStartAt(), dto.getEndAt(), excludeMeetingId)) {
            throw new OrganizerBusyInThisTimeException("Organizer is busy during this time");
        }
    }

    /**
     * Обновляет состав приглашённых без сброса статусов существующих участников.
     * <p>
     * Добавляет новых приглашённых (со статусом PENDING) и удаляет ушедших.
     * Статусы оставшихся участников (ACCEPTED/DECLINED) сохраняются.
     * </p>
     *
     * @param meeting    встреча
     * @param newLogins  новый список логинов приглашённых
     */
    private void updateInviteesOnly(Meeting meeting, List<String> newLogins) {
        List<Invitation> currentInvitations = invitationRepository.findByMeetingId(meeting.getId());

        Set<String> currentLogins = currentInvitations.stream()
                .map(inv -> inv.getInvitee().getLogin())
                .collect(Collectors.toSet());
        Set<String> newLoginsSet = new HashSet<>(newLogins);

        Set<String> toAdd = new HashSet<>(newLoginsSet);
        toAdd.removeAll(currentLogins);

        Set<String> toRemove = new HashSet<>(currentLogins);
        toRemove.removeAll(newLoginsSet);

        Map<String, Long> loginToInvitationId = currentInvitations.stream()
                .collect(Collectors.toMap(
                        inv -> inv.getInvitee().getLogin(),
                        Invitation::getId
                ));
        for (String login : toRemove) {
            Long invId = loginToInvitationId.get(login);
            invitationRepository.deleteById(invId);
        }

        if (!toAdd.isEmpty()) {
            var newUsers = resolveInvitees(new ArrayList<>(toAdd));
            newUsers.removeIf(user -> user.getId().equals(DEMO_ORGANIZER_ID));

            for (var user : newUsers) {
                Invitation inv = new Invitation();
                inv.setMeeting(meeting);
                inv.setInvitee(user);
                inv.setStatus(InvitationStatus.PENDING);
                invitationRepository.save(inv);
            }
        }
    }
}