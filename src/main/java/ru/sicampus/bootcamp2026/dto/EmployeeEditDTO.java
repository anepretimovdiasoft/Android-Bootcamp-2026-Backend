package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class EmployeeEditDTO {
    private String name;

    private String position;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^\\+7\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @URL(message = "Invalid URL format")
    private String photoUrl;
}