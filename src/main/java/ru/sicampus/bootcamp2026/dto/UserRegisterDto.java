package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {
    private @NotBlank String name;
    private @NotBlank String lastname;
    private @NotBlank String password;
    private @NotBlank String login;
    private @NotBlank String position;
}
