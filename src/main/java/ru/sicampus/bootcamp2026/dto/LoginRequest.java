package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}