package ru.sicampus.bootcamp2026.api.dto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;

public record InvitationRequest(
    @NotNull Long meetingId,
    @NotNull Long inviteeId,
    InvitationStatus status,
    OffsetDateTime respondedAt
) {
}
