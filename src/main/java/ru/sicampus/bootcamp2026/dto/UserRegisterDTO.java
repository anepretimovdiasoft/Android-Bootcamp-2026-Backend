package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String name;
    private String username;
    private String password;
}
