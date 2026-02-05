package com.teto.planner.dto;

import com.teto.planner.entity.ParticipantRole;
import com.teto.planner.entity.ParticipantStatus;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record MeetingParticipantDto(
        UserSummaryDto user,
        ParticipantRole role,
        ParticipantStatus status,
        LocalDate meetingDate,
        Integer startHour,
        OffsetDateTime createdAt,
        OffsetDateTime respondedAt
) {
}
