package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingDTO {
    private long id;
    private LocalDateTime start;
    private int duration;
    private String place;
    private String theme;
    private String description;
    private UserDTO creator;
}
