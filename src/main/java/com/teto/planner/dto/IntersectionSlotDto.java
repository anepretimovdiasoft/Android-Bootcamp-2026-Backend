package com.teto.planner.dto;

import java.util.List;

public record IntersectionSlotDto(
        Integer hour,
        IntersectionSlotStatus status,
        String label,
        List<UserSummaryDto> conflictedUsers
) {
}
