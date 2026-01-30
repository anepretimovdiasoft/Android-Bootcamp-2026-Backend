package ru.sicampus.bootcamp2026.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InvitationCreateDTO {
    @NotBlank
    private Long meetingId;

    @NotBlank
    private Long employeeId;

    private String message;
}
