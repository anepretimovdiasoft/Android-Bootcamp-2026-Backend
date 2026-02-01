package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class UsersDTO {

    private long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String login;
    private String email;
    private String password;
    private String department;
    private String position;
    private String photoUrl;

}
