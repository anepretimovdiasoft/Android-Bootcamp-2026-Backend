package ru.sicampus.bootcamp2026.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserResponse user;
}