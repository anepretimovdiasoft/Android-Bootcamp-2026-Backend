package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;

import java.time.LocalDateTime;

@Data
public class InvitationDTO {
    private long id;
    private String targetEmail;
    private Long meetingId;
    private InvitationStatus status;
    private LocalDateTime createdAt;
}
