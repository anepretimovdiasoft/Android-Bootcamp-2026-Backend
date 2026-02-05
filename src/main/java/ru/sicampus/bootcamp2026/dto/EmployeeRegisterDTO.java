package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRegisterDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Position cannot be blank")
    private String position;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, message = "Username must be at least 4 symbols")
    private String username;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 10, message = "Password must be at least 10 symbols")
    private String password;
}