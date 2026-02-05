package ru.sicampus.bootcamp2026.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record MeetingRequest(
    @NotNull Long organizerId,
    @NotBlank String title,
    String description,
    @NotNull OffsetDateTime startAt,
    @NotNull OffsetDateTime endAt
) {
}
