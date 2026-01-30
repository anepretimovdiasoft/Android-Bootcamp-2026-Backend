package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRegisterDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String position;

    @NotBlank
    @Size(min = 4)
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+7\\d{10}$")
    private String phoneNumber;

    @NotBlank
    @Size(min = 10)
    private String password;
}