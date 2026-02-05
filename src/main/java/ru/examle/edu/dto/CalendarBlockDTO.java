package ru.examle.edu.dto;

import lombok.Data;
import ru.examle.edu.entity.enums.BlockType;

import java.time.LocalDateTime;

@Data
public class CalendarBlockDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BlockType blockType;
    private boolean isRecurring;
    private String recurrencePattern;
    private LocalDateTime createdAt;
}
