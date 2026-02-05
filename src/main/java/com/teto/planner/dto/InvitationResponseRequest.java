package com.teto.planner.dto;

import jakarta.validation.constraints.NotNull;

public record InvitationResponseRequest(
        @NotNull InvitationResponseStatus status
) {
}
