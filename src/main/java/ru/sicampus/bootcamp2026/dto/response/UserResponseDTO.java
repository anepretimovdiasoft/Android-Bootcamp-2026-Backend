package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class UserResponseDTO {
    private long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private Instant createdAt;
    private Instant updatedAt;
}
