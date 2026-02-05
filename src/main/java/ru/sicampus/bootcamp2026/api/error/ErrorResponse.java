package ru.sicampus.bootcamp2026.api.error;

import java.time.OffsetDateTime;

public record ErrorResponse(
    OffsetDateTime timestamp,
    int status,
    String error,
    String message,
    String path
) {
}
