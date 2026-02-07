package ru.sicampus.bootcamp2026.dtos;

import lombok.Data;

@Data
public class InvitationDto {
    private Long id;
    private String status;
    private Long userId;
    private String userName;
    private Long meetingId;
    private String meetingTitle;
}

