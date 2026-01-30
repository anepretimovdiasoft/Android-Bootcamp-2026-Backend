package ru.sicampus.bootcamp2026.dto;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class MeetingCreateDTO {
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}