package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.toApp.TimeSlotDTO;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;
import ru.sicampus.bootcamp2026.entity.TimeSlot;
import ru.sicampus.bootcamp2026.entity.User;

@UtilityClass
public class TimeSlotMapper {
    public TimeSlotDTO convertToDto(TimeSlot dto) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setDate(dto.getDate().getDate());
        timeSlotDTO.setStartTime(dto.getStartTime());
        timeSlotDTO.setEndTime(dto.getEndTime());

        return timeSlotDTO;
    }
}
