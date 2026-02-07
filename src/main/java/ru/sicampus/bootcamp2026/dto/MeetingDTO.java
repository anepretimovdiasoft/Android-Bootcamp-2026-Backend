package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingDTO {
    private Long id;
    private String title;
    private String description;
    private Long organizerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
}
