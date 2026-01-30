package ru.sicampus.bootcamp2026.dto;

import lombok.Data;


@Data
public class InvitationMeetingDTO {
    private Long id;
    private String status;
    private String message;
    private MeetingDTO meeting;
}