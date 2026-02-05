package ru.sicampus.bootcamp2026.dto.fromApp;

import lombok.Data;

@Data
public class MeetingResponseDTO {
    private Long meetingId;
    private Long userId;
    private Boolean response;
}
