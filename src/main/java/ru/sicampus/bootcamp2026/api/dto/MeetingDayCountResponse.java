package ru.sicampus.bootcamp2026.api.dto;

import java.time.LocalDate;

public record MeetingDayCountResponse(
    LocalDate date,
    long count
) {
}

