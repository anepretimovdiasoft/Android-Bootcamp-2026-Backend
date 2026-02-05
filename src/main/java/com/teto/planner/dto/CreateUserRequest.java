package com.teto.planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank @Size(max = 64) String login,
        @NotBlank @Size(max = 128) String name,
        @NotBlank String password,
        @Size(max = 64) String telegramNick
) {
}
