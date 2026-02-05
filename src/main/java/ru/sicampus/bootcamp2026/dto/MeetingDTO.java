package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MeetingDTO {
    private long id;
    private String title;
    private long organizerId;
    private LocalDate date;
    private int startHour;
    private LocalDateTime createdAt;
}
