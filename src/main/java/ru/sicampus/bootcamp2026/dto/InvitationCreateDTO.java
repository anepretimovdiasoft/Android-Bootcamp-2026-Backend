package ru.sicampus.bootcamp2026.dto;


import lombok.Data;

@Data
public class InvitationCreateDTO {
    private Long meetingId;
    private Long employeeId;
    private String message;
}
