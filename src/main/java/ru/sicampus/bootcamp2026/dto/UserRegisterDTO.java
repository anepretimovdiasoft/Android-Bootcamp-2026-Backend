package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String departmentName;
}
