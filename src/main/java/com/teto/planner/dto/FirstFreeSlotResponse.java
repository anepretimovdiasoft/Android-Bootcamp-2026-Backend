package com.teto.planner.dto;

import java.time.LocalDate;
import java.util.List;

public record FirstFreeSlotResponse(
        LocalDate meetingDate,
        UserSummaryDto organizer,
        List<UserSummaryDto> users,
        IntersectionSlotDto slot
) {
}
