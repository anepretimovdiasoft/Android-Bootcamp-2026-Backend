package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private @NotBlank String login;
    private @NotBlank String name;
    private @NotBlank String lastname;
    private String aboutMe;
    private @NotBlank String position;
    private String photoUrl;
}