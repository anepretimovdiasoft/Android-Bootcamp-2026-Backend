package ru.sicampus.bootcamp2026.api.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record PersonResponse(
    Long id,
    String email,
    String fullName,
    String department,
    String position,
    OffsetDateTime createdAt,
    List<String> authorities
) {
}

