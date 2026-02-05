package com.teto.planner.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserDto(
        UUID id,
        String login,
        String name,
        String telegramNick,
        String avatarUrl,
        String avatarContentType,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
