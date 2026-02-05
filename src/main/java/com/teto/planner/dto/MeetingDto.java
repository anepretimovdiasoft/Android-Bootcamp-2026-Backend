package com.teto.planner.dto;

import com.teto.planner.entity.MeetingStatus;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record MeetingDto(
        UUID id,
        UserSummaryDto organizer,
        LocalDate meetingDate,
        Integer startHour,
        Integer durationHours,
        String title,
        String description,
        RoomDto room,
        MeetingStatus status,
        List<MeetingParticipantDto> participants,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
