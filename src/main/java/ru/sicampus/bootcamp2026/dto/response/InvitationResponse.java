package ru.sicampus.bootcamp2026.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvitationResponse {
    private Long id;
    private MeetResponse meet;
    private UserResponse user;
    private String status; // "PENDING", "ACCEPTED", "DECLINED"
    private LocalDateTime respondedAt;
}