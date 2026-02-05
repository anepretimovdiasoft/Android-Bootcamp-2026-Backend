package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingInputDTO {
    private LocalDateTime start;
    private int duration;
    private String place;
    private String theme;
    private String description;
}
