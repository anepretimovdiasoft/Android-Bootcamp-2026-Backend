package ru.sicampus.bootcamp2026.dto.request;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String fullName;

    private String avatarUrl;
}
