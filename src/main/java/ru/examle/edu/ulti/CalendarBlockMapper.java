package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.CalendarBlockDTO;
import ru.examle.edu.entity.CalendarBlock;

@Component
public class CalendarBlockMapper {
    public CalendarBlockDTO toDTO(CalendarBlock block) {
        if (block == null) return null;
        CalendarBlockDTO dto = new CalendarBlockDTO();
        dto.setId(block.getId());
        dto.setUserId(block.getUserId());
        dto.setTitle(block.getTitle());
        dto.setDescription(block.getDescription());
        dto.setStartTime(block.getStartTime());
        dto.setEndTime(block.getEndTime());
        dto.setBlockType(block.getBlockType());
        dto.setRecurring(block.isRecurring());
        dto.setRecurrencePattern(block.getRecurrencePattern());
        dto.setCreatedAt(block.getCreatedAt());
        return dto;
    }

    public CalendarBlock toEntity(CalendarBlockDTO dto) {
        if (dto == null) return null;
        CalendarBlock block = new CalendarBlock();
        if (dto.getId() != null) block.setId(dto.getId());
        if (dto.getUserId() != null) block.setUserId(dto.getUserId());
        block.setTitle(dto.getTitle());
        block.setDescription(dto.getDescription());
        block.setStartTime(dto.getStartTime());
        block.setEndTime(dto.getEndTime());
        if (dto.getBlockType() != null) block.setBlockType(dto.getBlockType());
        block.setRecurring(dto.isRecurring());
        block.setRecurrencePattern(dto.getRecurrencePattern());
        return block;
    }
}
