package ru.sicampus.bootcamp2026.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvitationDTO {
    private String invitationId;
    private String topic;
    private LocalDateTime dateTime;
    private String organizerName;
}