package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvitationDTO {
    private long id;
    private long meetingId;
    private long inviteeId;
    private int status;
    private LocalDateTime invitedAt;
    private LocalDateTime respondedAt;
}
