package com.teto.planner.mapper;

import com.teto.planner.dto.MeetingDto;
import com.teto.planner.dto.MeetingParticipantDto;
import com.teto.planner.dto.RoomDto;
import com.teto.planner.dto.UserSummaryDto;
import com.teto.planner.dto.LoadStatus;
import com.teto.planner.entity.MeetingEntity;
import com.teto.planner.entity.MeetingParticipantEntity;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {
    private final UserMapper userMapper;
    private final RoomMapper roomMapper;

    public MeetingMapper(UserMapper userMapper, RoomMapper roomMapper) {
        this.userMapper = userMapper;
        this.roomMapper = roomMapper;
    }

    public MeetingDto toDto(MeetingEntity meeting) {
        UserSummaryDto organizer = userMapper.toSummary(meeting.getOrganizer(), null, null);
        RoomDto room = meeting.getRoom() == null ? null : roomMapper.toDto(meeting.getRoom());
        List<MeetingParticipantDto> participants = meeting.getParticipants().stream()
                .map(this::toParticipant)
                .collect(Collectors.toList());

        return new MeetingDto(
                meeting.getId(),
                organizer,
                meeting.getMeetingDate(),
                meeting.getStartHour() != null ? meeting.getStartHour().intValue() : null,
                meeting.getDurationHours() != null ? meeting.getDurationHours().intValue() : null,
                meeting.getTitle(),
                meeting.getDescription(),
                room,
                meeting.getStatus(),
                participants,
                meeting.getCreatedAt(),
                meeting.getUpdatedAt()
        );
    }

    public MeetingDto toDto(MeetingEntity meeting, Map<UUID, Integer> busyHours, Map<UUID, LoadStatus> loadStatus) {
        UserSummaryDto organizer = userMapper.toSummary(
                meeting.getOrganizer(),
                busyHours.getOrDefault(meeting.getOrganizer().getId(), 0),
                loadStatus.getOrDefault(meeting.getOrganizer().getId(), LoadStatus.LOW)
        );
        RoomDto room = meeting.getRoom() == null ? null : roomMapper.toDto(meeting.getRoom());
        List<MeetingParticipantDto> participants = meeting.getParticipants().stream()
                .map(p -> toParticipant(p, busyHours, loadStatus))
                .collect(Collectors.toList());

        return new MeetingDto(
                meeting.getId(),
                organizer,
                meeting.getMeetingDate(),
                meeting.getStartHour() != null ? meeting.getStartHour().intValue() : null,
                meeting.getDurationHours() != null ? meeting.getDurationHours().intValue() : null,
                meeting.getTitle(),
                meeting.getDescription(),
                room,
                meeting.getStatus(),
                participants,
                meeting.getCreatedAt(),
                meeting.getUpdatedAt()
        );
    }

    public MeetingParticipantDto toParticipant(MeetingParticipantEntity participant) {
        UserSummaryDto user = userMapper.toSummary(participant.getUser(), null, null);
        return new MeetingParticipantDto(
                user,
                participant.getRole(),
                participant.getStatus(),
                participant.getMeetingDate(),
                participant.getStartHour() != null ? participant.getStartHour().intValue() : null,
                participant.getCreatedAt(),
                participant.getRespondedAt()
        );
    }

    public MeetingParticipantDto toParticipant(MeetingParticipantEntity participant,
                                               Map<UUID, Integer> busyHours,
                                               Map<UUID, LoadStatus> loadStatus) {
        UUID userId = participant.getUser().getId();
        UserSummaryDto user = userMapper.toSummary(
                participant.getUser(),
                busyHours.getOrDefault(userId, 0),
                loadStatus.getOrDefault(userId, LoadStatus.LOW)
        );
        return new MeetingParticipantDto(
                user,
                participant.getRole(),
                participant.getStatus(),
                participant.getMeetingDate(),
                participant.getStartHour() != null ? participant.getStartHour().intValue() : null,
                participant.getCreatedAt(),
                participant.getRespondedAt()
        );
    }
}
