package com.teto.planner.dto;

import java.time.LocalDate;
import java.util.List;

public record IntersectionResponse(
        LocalDate meetingDate,
        UserSummaryDto organizer,
        List<UserSummaryDto> users,
        List<IntersectionSlotDto> slots
) {
}
