package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String firstName;
    private String secondName;
    private String patronymic;
    private String position;
    private String email;
    private String password;
    private String passwordAgain;
}
