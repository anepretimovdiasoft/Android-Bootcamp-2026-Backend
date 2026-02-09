package com.teto.planner.service;

import com.teto.planner.dto.FirstFreeSlotResponse;
import com.teto.planner.dto.IntersectionResponse;
import com.teto.planner.dto.IntersectionSlotDto;
import com.teto.planner.dto.IntersectionSlotStatus;
import com.teto.planner.dto.UserSummaryDto;
import com.teto.planner.entity.MeetingParticipantEntity;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.BadRequestException;
import com.teto.planner.mapper.UserMapper;
import com.teto.planner.repository.MeetingParticipantRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchedulingService {
    private final MeetingParticipantRepository participantRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public SchedulingService(MeetingParticipantRepository participantRepository,
                             UserService userService,
                             UserMapper userMapper) {
        this.participantRepository = participantRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public IntersectionResponse getIntersection(UserEntity organizer, LocalDate meetingDate, List<UUID> userIds) {
        List<UUID> uniqueUserIds = new ArrayList<>(new HashSet<>(userIds));
        uniqueUserIds.remove(organizer.getId());

        Map<UUID, UserEntity> userById = new HashMap<>();
        for (UUID userId : uniqueUserIds) {
            UserEntity user = userService.findUser(userId);
            userById.put(userId, user);
        }

        List<UUID> allIds = new ArrayList<>(uniqueUserIds);
        allIds.add(organizer.getId());

        Map<UUID, Set<Short>> busyByUser = buildBusyByUser(allIds, meetingDate);

        List<UserSummaryDto> users = uniqueUserIds.stream()
                .map(userById::get)
                .map(u -> userMapper.toSummary(u, null, null))
                .toList();
        UserSummaryDto organizerSummary = userMapper.toSummary(organizer, null, null);

        List<IntersectionSlotDto> slots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int currentHour = LocalTime.now().getHour();
        for (int hour = 0; hour < 24; hour++) {
            boolean past = meetingDate.isBefore(today)
                    || (meetingDate.isEqual(today) && hour <= currentHour);
            boolean organizerBusy = busyByUser.getOrDefault(organizer.getId(), Set.of())
                    .contains((short) hour);

            if (past || organizerBusy) {
                slots.add(new IntersectionSlotDto(hour, IntersectionSlotStatus.DISABLED, label(hour), List.of()));
                continue;
            }

            List<UserSummaryDto> conflicted = new ArrayList<>();
            for (UUID userId : uniqueUserIds) {
                if (busyByUser.getOrDefault(userId, Set.of()).contains((short) hour)) {
                    conflicted.add(userMapper.toSummary(userById.get(userId), null, null));
                }
            }

            IntersectionSlotStatus status = conflicted.isEmpty()
                    ? IntersectionSlotStatus.GREEN
                    : IntersectionSlotStatus.YELLOW;
            slots.add(new IntersectionSlotDto(hour, status, label(hour), conflicted));
        }

        return new IntersectionResponse(meetingDate, organizerSummary, users, slots);
    }

    @Transactional(readOnly = true)
    public FirstFreeSlotResponse getFirstFreeSlot(UserEntity organizer, LocalDate meetingDate,
                                                  List<UUID> userIds, Integer durationHours) {
        int duration = durationHours != null ? durationHours : 1;
        if (duration < 1 || duration > 24) {
            throw new BadRequestException("VALIDATION_ERROR", "durationHours must be between 1 and 24");
        }

        List<UUID> uniqueUserIds = new ArrayList<>(new HashSet<>(userIds));
        uniqueUserIds.remove(organizer.getId());

        Map<UUID, UserEntity> userById = new HashMap<>();
        for (UUID userId : uniqueUserIds) {
            UserEntity user = userService.findUser(userId);
            userById.put(userId, user);
        }

        List<UUID> allIds = new ArrayList<>(uniqueUserIds);
        allIds.add(organizer.getId());
        Map<UUID, Set<Short>> busyByUser = buildBusyByUser(allIds, meetingDate);

        List<UserSummaryDto> users = uniqueUserIds.stream()
                .map(userById::get)
                .map(u -> userMapper.toSummary(u, null, null))
                .toList();
        UserSummaryDto organizerSummary = userMapper.toSummary(organizer, null, null);

        IntersectionSlotDto firstFree = findFirstFreeSlot(meetingDate, duration, organizer, uniqueUserIds, userById, busyByUser);
        return new FirstFreeSlotResponse(
                meetingDate,
                organizerSummary,
                users,
                firstFree
        );
    }

    private IntersectionSlotDto findFirstFreeSlot(LocalDate meetingDate, int duration, UserEntity organizer,
                                                  List<UUID> userIds, Map<UUID, UserEntity> userById,
                                                  Map<UUID, Set<Short>> busyByUser) {
        LocalDate today = LocalDate.now();
        int currentHour = LocalTime.now().getHour();

        for (int hour = 0; hour < 24; hour++) {
            if (hour + duration > 24) {
                break;
            }
            if (rangeIsPast(meetingDate, hour, duration, today, currentHour)) {
                continue;
            }
            if (hasBusyInRange(busyByUser.getOrDefault(organizer.getId(), Set.of()), hour, duration)) {
                continue;
            }
            boolean conflicted = false;
            for (UUID userId : userIds) {
                if (hasBusyInRange(busyByUser.getOrDefault(userId, Set.of()), hour, duration)) {
                    conflicted = true;
                    break;
                }
            }
            if (!conflicted) {
                return new IntersectionSlotDto(hour, IntersectionSlotStatus.GREEN, label(hour, duration), List.of());
            }
        }
        return null;
    }

    private boolean rangeIsPast(LocalDate meetingDate, int startHour, int duration, LocalDate today, int currentHour) {
        if (meetingDate.isBefore(today)) {
            return true;
        }
        if (meetingDate.isAfter(today)) {
            return false;
        }
        for (int i = 0; i < duration; i++) {
            int hour = startHour + i;
            if (hour <= currentHour) {
                return true;
            }
        }
        return false;
    }

    private boolean hasBusyInRange(Set<Short> busyHours, int startHour, int duration) {
        for (int i = 0; i < duration; i++) {
            if (busyHours.contains((short) (startHour + i))) {
                return true;
            }
        }
        return false;
    }

    private Map<UUID, Set<Short>> buildBusyByUser(List<UUID> userIds, LocalDate meetingDate) {
        List<MeetingParticipantEntity> busy = participantRepository.findAcceptedForUsersOnDate(userIds, meetingDate);
        Map<UUID, Set<Short>> busyByUser = new HashMap<>();
        for (MeetingParticipantEntity participant : busy) {
            short start = participant.getStartHour();
            int duration = participant.getMeeting().getDurationHours() != null
                    ? participant.getMeeting().getDurationHours()
                    : 1;
            Set<Short> hours = busyByUser.computeIfAbsent(participant.getUser().getId(), key -> new HashSet<>());
            for (int i = 0; i < duration; i++) {
                int hour = start + i;
                if (hour >= 24) {
                    break;
                }
                hours.add((short) hour);
            }
        }
        return busyByUser;
    }

    private String label(int hour) {
        return label(hour, 1);
    }

    private String label(int hour, int duration) {
        int end = (hour + duration) % 24;
        return String.format("%02d:00-%02d:00", hour, end);
    }
}
