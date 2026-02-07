package com.teto.planner.service;

import com.teto.planner.dto.IntersectionResponse;
import com.teto.planner.dto.IntersectionSlotDto;
import com.teto.planner.dto.IntersectionSlotStatus;
import com.teto.planner.dto.UserSummaryDto;
import com.teto.planner.entity.MeetingParticipantEntity;
import com.teto.planner.entity.UserEntity;
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

        List<MeetingParticipantEntity> busy = participantRepository.findAcceptedForUsersOnDate(allIds, meetingDate);
        Map<UUID, Set<Short>> busyByUser = new HashMap<>();
        for (MeetingParticipantEntity participant : busy) {
            busyByUser.computeIfAbsent(participant.getUser().getId(), key -> new HashSet<>())
                    .add(participant.getStartHour());
        }

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

    private String label(int hour) {
        int next = (hour + 1) % 24;
        return String.format("%02d:00-%02d:00", hour, next);
    }
}
