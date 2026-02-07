package com.teto.planner.dto;

import java.util.UUID;

public record UserSummaryDto(
        UUID id,
        String login,
        String name,
        String telegramNick,
        String bio,
        Integer busyHours,
        LoadStatus loadStatus,
        String avatarUrl
) {
}
