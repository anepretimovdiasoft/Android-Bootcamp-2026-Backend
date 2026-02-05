package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class UsersRegisterDTO {
    private String login;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

}

