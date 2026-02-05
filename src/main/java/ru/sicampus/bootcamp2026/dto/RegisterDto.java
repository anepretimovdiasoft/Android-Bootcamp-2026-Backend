package ru.sicampus.bootcamp2026.dto;


import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String username;
    private String password;
    private String position;
}
