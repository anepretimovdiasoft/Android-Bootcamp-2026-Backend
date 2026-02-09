package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class UsersDTO {
    private long id;
    private String name;
    private String email;
    private String username;
    private String avatarUrl;
}