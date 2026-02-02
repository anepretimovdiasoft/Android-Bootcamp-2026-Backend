package ru.sicampus.bootcamp2026.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitationCreateDTO {
    @NotNull(message = "Meeting ID cannot be blank")
    private Long meetingId;

    @NotNull(message = "Employee ID cannot be blank")
    private Long employeeId;

    private String message;
}
