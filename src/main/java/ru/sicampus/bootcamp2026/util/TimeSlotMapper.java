package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.toApp.TimeSlotDTO;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class TimeSlotMapper {
    public TimeSlotDTO convertToDto(LocalDate date, LocalTime start, LocalTime end) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setDate(date);
        timeSlotDTO.setStartTime(start);
        timeSlotDTO.setEndTime(end);

        return timeSlotDTO;
    }
}
