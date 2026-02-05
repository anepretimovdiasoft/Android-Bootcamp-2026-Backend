package ru.sicampus.bootcamp2026.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitationCreateDTO {
    @NotNull(message = "Meeting ID cannot be blank")
    private Long meetingId;

    @NotBlank(message = "Employee UN cannot be blank")
    private String employeeUsername;

    private String message;
}
