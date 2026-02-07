package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {
    public record LoginRequest(String login, String password) {}
    public record LoginResponse(String token, String name, String position, long id) {}
    public record ResetPasswordRequest(String token, String newPassword) {}
    public record ForgotPasswordRequest(String email) {}
}