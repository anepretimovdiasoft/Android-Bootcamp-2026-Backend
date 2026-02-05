package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String secondName;
    private String patronymic;
    private String position;
    private String email;
    private LocalDateTime createdAt;
}
