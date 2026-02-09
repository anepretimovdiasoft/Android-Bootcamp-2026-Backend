package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class InvitationsDTO {
    private String meetingCreatorName;
    private String invitedUserName;
    private LocalDateTime meetingDate;
    private Boolean accepted;
}