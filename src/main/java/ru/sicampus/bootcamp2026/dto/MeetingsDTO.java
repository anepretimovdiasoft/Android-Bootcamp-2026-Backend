package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MeetingsDTO {
    private long id;
    private String creatorName;
    private String date;
    private String title;
    private String description;
}