package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private long id;

    private String name;

    private String email;

    private String photoUrl;

    private String departmentName;

    private LocalDateTime createdAt;
}
