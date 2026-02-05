package ru.sicampus.bootcamp2026.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @Email
    @NotEmpty(message = "email couldn't be empty")
    private String email;

    private String name;

    @NotEmpty(message = "password couldn't be empty")
    private String password;

    private String position;

    private String photoUrl;
}
