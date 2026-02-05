package ru.sicampus.bootcamp2026.api.dto;

public record PersonUpdateRequest(
    String fullName,
    String department,
    String position,
    String password
) {
}

