package ru.sicampus.bootcamp2026.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScheduleEntryDTO {
    private String topic;
    private LocalDateTime dateTime;
}