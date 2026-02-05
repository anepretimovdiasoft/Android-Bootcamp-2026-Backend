package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserCreateDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 4, max = 32)
    private String password;

    private String avatarUrl;
}
