package ru.sicampus.bootcamp2026.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private Long organizerId;
    private String organizerName;
}
