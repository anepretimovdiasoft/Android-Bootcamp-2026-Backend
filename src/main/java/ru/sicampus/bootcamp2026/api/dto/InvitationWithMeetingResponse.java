package ru.sicampus.bootcamp2026.api.dto;

import java.time.OffsetDateTime;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;

public record InvitationWithMeetingResponse(
    Long id,
    Long inviteeId,
    InvitationStatus status,
    OffsetDateTime respondedAt,
    OffsetDateTime createdAt,
    MeetingResponse meeting
) {
}

