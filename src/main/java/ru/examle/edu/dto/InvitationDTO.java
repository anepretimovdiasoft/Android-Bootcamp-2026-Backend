package ru.examle.edu.dto;

import lombok.Data;
import ru.examle.edu.entity.enums.InvitationStatus;

import java.time.LocalDateTime;

@Data
public class InvitationDTO {
    private Long id;
    private Long meetingId;
    private Long userId;
    private InvitationStatus responseStatus;
    private String responseComment;
    private boolean isRequired;
    private LocalDateTime respondedAt;
    private LocalDateTime createdAt;
}
