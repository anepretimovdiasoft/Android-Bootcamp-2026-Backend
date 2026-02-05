package ru.sicampus.bootcamp2026.api.dto;

import java.time.OffsetDateTime;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;

public record InvitationResponse(
    Long id,
    Long meetingId,
    Long inviteeId,
    InvitationStatus status,
    OffsetDateTime respondedAt,
    OffsetDateTime createdAt
) {
}
