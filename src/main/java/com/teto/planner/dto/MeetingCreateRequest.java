package com.teto.planner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record MeetingCreateRequest(
        @NotNull LocalDate meetingDate,
        @NotNull @Min(0) @Max(23) Integer startHour,
        @Min(1) @Max(24) Integer durationHours,
        @NotBlank @Size(max = 256) String title,
        String description,
        UUID roomId,
        List<UUID> participantIds
) {
}
