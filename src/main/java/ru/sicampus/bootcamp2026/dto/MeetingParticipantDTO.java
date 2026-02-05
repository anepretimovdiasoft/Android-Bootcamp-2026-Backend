package ru.sicampus.bootcamp2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingParticipantDTO {
    private Long id;
    private Long meetingId;
    private Long userId;
    private String status;
}