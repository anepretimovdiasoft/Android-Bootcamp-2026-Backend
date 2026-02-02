package ru.sicampus.bootcamp2026.dto;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

public class UserDtos {

    public record CreateUserRequest(
            @NotBlank @Size(max = 255) String role,
            @NotBlank @Size(max = 255) String name,
            @NotBlank @Size(max = 255) String login

            ) {}

    public record UpdateUserRequest(
            @NotBlank @Size(max = 255) String role,
            @NotBlank @Size(max = 255) String name,
            @NotBlank @Size(max = 255) String login

    ) {}

    public record InvitationDecisionRequest(
            @NotBlank
            @Size(max = 32)
            @jakarta.validation.constraints.Pattern(regexp = "ACCEPTED|REJECTED|PENDING")
            String status
    ) {}

    public record UserResponse(Long id, String role, String name, String login) {}
}