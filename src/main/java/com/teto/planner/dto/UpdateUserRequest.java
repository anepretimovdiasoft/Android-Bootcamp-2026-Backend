package com.teto.planner.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(max = 128) String name,
        @Size(max = 64) String telegramNick
) {
}
