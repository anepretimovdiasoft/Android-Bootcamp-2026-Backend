package ru.sicampus.bootcamp2026.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.FreeTimeResponse;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.exception.MeetingConflictException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.model.Meeting;
import ru.sicampus.bootcamp2026.model.MeetingParticipant;
import ru.sicampus.bootcamp2026.model.MeetingStatus;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;
import ru.sicampus.bootcamp2026.model.User;
import ru.sicampus.bootcamp2026.repository.MeetingParticipantRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserRepository userRepository;

    @Override
    public MeetingResponse createMeeting(UUID organizerId, CreateMeetingRequest request) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException("Организатор не найден"));

        if (meetingRepository.existsByOrganizerIdAndTimeOverlap(
                organizerId,
                request.getStartTime(),
                request.getEndTime())) {
            throw new MeetingConflictException(
                    "У организатора уже есть встреча в это время");
        }

        Meeting meeting = Meeting.builder()
                .organizer_id(organizer)
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .meetingStatus(MeetingStatus.SCHEDULED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        meeting = meetingRepository.save(meeting);

        MeetingParticipant organizerParticipant = new MeetingParticipant();
        organizerParticipant.setMeeting(meeting);
        organizerParticipant.setUser(organizer);
        organizerParticipant.setStatus(ParticipantStatus.CONFIRMED);
        meetingParticipantRepository.save(organizerParticipant);

        if (request.getParticipantIds() != null && !request.getParticipantIds().isEmpty()) {
            for (UUID participantId : request.getParticipantIds()) {
                if (participantId.equals(organizerId)) {
                    continue;
                }

                if (meetingRepository.existsByUserIdAndTimeOverlap(
                        participantId,
                        request.getStartTime(),
                        request.getEndTime())) {
                    throw new MeetingConflictException(
                            "У участника с ID " + participantId + " уже есть встреча в это время");
                }

                User participant = userRepository.findById(participantId)
                        .orElseThrow(() -> new UserNotFoundException(
                                "Участник с ID " + participantId + " не найден"));

                MeetingParticipant participantEntity = new MeetingParticipant();
                participantEntity.setMeeting(meeting);
                participantEntity.setUser(participant);
                participantEntity.setStatus(ParticipantStatus.PENDING);
                meetingParticipantRepository.save(participantEntity);
            }
        }

        return MeetingResponse.fromMeeting(meeting);
    }

    @Override
    public MeetingResponse getMeetingById(UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException("Встреча не найдена"));

        return MeetingResponse.fromMeeting(meeting);
    }

    @Override
    public Page<MeetingResponse> getUserMeetings(UUID userId, Pageable pageable) {
        return meetingRepository.findAllByUserId(userId, pageable)
                .map(MeetingResponse::fromMeeting);
    }

    @Override
    public MeetingResponse cancelMeeting(UUID organizerId, UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException("Встреча не найдена"));

        if (!meeting.getOrganizer_id().getId().equals(organizerId)) {
            throw new SecurityException("Только организатор может отменить встречу");
        }

        meeting.setMeetingStatus(MeetingStatus.CANCELLED);
        meeting.setUpdatedAt(Instant.now());
        meetingRepository.save(meeting);

        return MeetingResponse.fromMeeting(meeting);
    }

    @Override
    public void deleteMeeting(UUID organizerId, UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException("Встреча не найдена"));

        if (!meeting.getOrganizer_id().getId().equals(organizerId)) {
            throw new SecurityException("Только организатор может удалить встречу");
        }

        meetingParticipantRepository.deleteByMeeting_Id(meetingId);

        meetingRepository.delete(meeting);
    }

    @Override
    public Page<MeetingResponse> getMeetingsByStatus(UUID userId, MeetingStatus status, Pageable pageable) {
        return meetingRepository.findByUserIdAndStatus(userId, status, pageable)
                .map(MeetingResponse::fromMeeting);
    }

    @Override
    public List<FreeTimeResponse.FreeTimeSlot> findFreeTimeSlots(List<UUID> userIds, int durationMinutes) {
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("Список пользователей не может быть пустым");
        }

        Instant now = Instant.now();
        Instant searchEnd = now.plus(Duration.ofDays(14)); // Диапазон поиска: следующие 14 дней

        Map<UUID, List<TimeSlot>> busySlotsByUser = new HashMap<>();

        for (UUID userId : userIds) {
            List<TimeSlot> busySlots = getBusySlotsForUser(userId, now, searchEnd);
            busySlotsByUser.put(userId, busySlots);
        }

        List<FreeTimeResponse.FreeTimeSlot> freeSlots = findCommonFreeSlots(
                busySlotsByUser,
                now,
                searchEnd,
                durationMinutes
        );

        return freeSlots;
    }

    /**
     * Получает все занятые слоты пользователя (только подтверждённые встречи)
     */
    private List<TimeSlot> getBusySlotsForUser(UUID userId, Instant start, Instant end) {
        List<MeetingParticipant> confirmedMeetings = meetingParticipantRepository
                .findByUserIdAndStatus(userId, ParticipantStatus.CONFIRMED);

        return confirmedMeetings.stream()
                .map(mp -> {
                    Meeting meeting = mp.getMeeting();
                    if (meeting.getStartTime().isBefore(end) && meeting.getEndTime().isAfter(start)) {
                        return new TimeSlot(meeting.getStartTime(), meeting.getEndTime());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Находит общие свободные слоты для всех пользователей
     */
    private List<FreeTimeResponse.FreeTimeSlot> findCommonFreeSlots(
            Map<UUID, List<TimeSlot>> busySlotsByUser,
            Instant searchStart,
            Instant searchEnd,
            int durationMinutes
    ) {
        List<FreeTimeResponse.FreeTimeSlot> result = new ArrayList<>();

        Instant current = roundToNextHour(searchStart);

        while (current.isBefore(searchEnd)) {
            Instant slotEnd = current.plus(Duration.ofMinutes(durationMinutes));

            Instant finalCurrent = current;
            boolean isFreeForAll = busySlotsByUser.values().stream()
                    .allMatch(busySlots -> isSlotFree(finalCurrent, slotEnd, busySlots));

            if (isFreeForAll) {
                result.add(FreeTimeResponse.FreeTimeSlot.builder()
                        .startTime(current)
                        .endTime(slotEnd)
                        .build());
            }

            current = current.plus(Duration.ofHours(1));
        }

        return result;
    }

    /**
     * Проверяет, свободен ли слот от пересечений с занятыми слотами
     */
    private boolean isSlotFree(Instant slotStart, Instant slotEnd, List<TimeSlot> busySlots) {
        return busySlots.stream()
                .noneMatch(busy ->
                        !(slotEnd.isBefore(busy.start) || slotStart.isAfter(busy.end))
                );
    }

    /**
     * Округляет время до следующего часа
     */
    private Instant roundToNextHour(Instant instant) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime rounded = dateTime.truncatedTo(ChronoUnit.HOURS);

        if (!dateTime.equals(rounded)) {
            rounded = rounded.plusHours(1);
        }

        return rounded.toInstant(ZoneOffset.UTC);
    }

    /**
     * Вспомогательный класс для хранения временного слота
     */
    @AllArgsConstructor
    @Getter
    private static class TimeSlot {
        private final Instant start;
        private final Instant end;
    }
}

