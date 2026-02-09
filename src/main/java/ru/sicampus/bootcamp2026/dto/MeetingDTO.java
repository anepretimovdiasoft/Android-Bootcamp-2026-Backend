package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class MeetingDTO {
    private Long id;
    private String title;
    private String description;
    private Long organizerId;
    private String startTime;
    private String endTime;
    private String createdAt;
}
