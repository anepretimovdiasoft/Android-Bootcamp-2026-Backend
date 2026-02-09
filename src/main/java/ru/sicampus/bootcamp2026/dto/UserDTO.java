package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String fullName;
    private String pfpUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
