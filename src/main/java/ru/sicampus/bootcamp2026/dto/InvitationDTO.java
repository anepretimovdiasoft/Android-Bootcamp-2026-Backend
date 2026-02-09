package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class InvitationDTO {
    private Long id;
    private Long meetingId;
    private String personName;
    private String status;
    private String respondedAt;
    private String createdAt;
}
