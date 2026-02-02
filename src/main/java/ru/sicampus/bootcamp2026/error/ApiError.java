package ru.sicampus.bootcamp2026.error;

import java.time.OffsetDateTime;

public record ApiError(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}