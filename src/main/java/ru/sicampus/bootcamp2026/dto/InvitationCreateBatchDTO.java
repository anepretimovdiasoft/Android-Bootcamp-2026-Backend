package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class InvitationCreateBatchDTO {
    @NotNull(message = "Meeting ID cannot be blank")
    private Long meetingId;

    @NotNull(message = "Employee UN cannot be blank")
    private List<String> employeeUsernames;

    private String message;
}
