package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.entity.Meeting;

@UtilityClass
public class MeetingMapper {
    public MeetingDto toDto(Meeting entity) {
        var dto = new MeetingDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setStartAt(entity.getStartAt());
        dto.setEndAt(entity.getEndAt());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setLocation(entity.getLocation());
        dto.setUrl(entity.getUrl());

        return dto;
    }
}
