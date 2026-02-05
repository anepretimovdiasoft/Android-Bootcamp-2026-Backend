package ru.sicampus.bootcamp2026.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PersonRegisterRequest(
    @NotBlank @Email String email,
    @NotBlank String password,
    String fullName,
    String department,
    String position
) {
}

