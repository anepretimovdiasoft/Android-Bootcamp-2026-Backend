package ru.sicampus.bootcamp2026.api.dto;

import jakarta.validation.constraints.NotNull;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;

public record InvitationRespondRequest(
    @NotNull InvitationStatus status
) {
}

