package com.teto.planner.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UserMeDto(
        UUID id,
        String login,
        String name,
        String telegramNick,
        String bio,
        String avatarUrl,
        String avatarContentType,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<RoleDto> roles
) {
}
