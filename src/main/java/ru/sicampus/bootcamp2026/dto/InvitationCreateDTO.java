package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class InvitationCreateDTO {
    String emailTarget;
    Long meetingId;
}
