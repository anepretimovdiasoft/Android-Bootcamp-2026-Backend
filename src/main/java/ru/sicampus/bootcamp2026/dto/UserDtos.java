package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class UserDtos {

    public record CreateUserRequest(
            @NotBlank @Size(max = 255) String position,
            @NotBlank @Size(max = 255) String name,
            @NotBlank @Size(max = 255) String login,
            @NotBlank @Size(max = 255) String password
    ) {}

    public record UpdateUserRequest(
            @NotBlank @Size(max = 255) String position,
            @NotBlank @Size(max = 255) String name,
            @NotBlank @Size(max = 255) String login,
            String phone,
            LocalDate birthDate,
            String avatarUrl
    ) {}

    public record UserResponse(
            Long id,
            String position,
            String name,
            String email,
            String phone,
            LocalDate birthDate,
            String avatarUrl
    ) {}

    public record InvitationDecisionRequest(
            @NotBlank
            @Size(max = 32)
            @Pattern(regexp = "ACCEPTED|REJECTED|PENDING")
            String status
    ) {}
}