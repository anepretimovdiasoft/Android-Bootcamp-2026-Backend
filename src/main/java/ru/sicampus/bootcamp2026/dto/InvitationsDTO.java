package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvitationsDTO {
    private String meetingCreatorName;
    private String invitedUserName;
    private LocalDate meetingDate;
    private boolean accepted;
}