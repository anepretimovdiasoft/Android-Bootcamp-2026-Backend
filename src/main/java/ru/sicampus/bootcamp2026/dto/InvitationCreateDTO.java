package ru.sicampus.bootcamp2026.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitationCreateDTO {
    @NotNull
    private Long meetingId;

    @NotNull
    private Long employeeId;

    private String message;
}
