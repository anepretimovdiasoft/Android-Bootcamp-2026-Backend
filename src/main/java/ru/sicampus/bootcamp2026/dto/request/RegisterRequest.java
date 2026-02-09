package ru.sicampus.bootcamp2026.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String position;
}