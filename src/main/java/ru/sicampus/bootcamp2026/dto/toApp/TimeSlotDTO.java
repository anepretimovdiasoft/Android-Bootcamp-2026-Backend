package ru.sicampus.bootcamp2026.dto.toApp;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeSlotDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
