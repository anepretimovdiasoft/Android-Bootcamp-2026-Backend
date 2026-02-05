package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.entity.Meetings;
import ru.sicampus.bootcamp2026.service.MeetingsService;

@UtilityClass
public class MeetingsMapper {

    public MeetingsDTO convertToDTO(Meetings meetings) {
        MeetingsDTO meetingsDTO = new MeetingsDTO();
        meetingsDTO.setId(meetings.getId());
        meetingsDTO.setDate(meetings.getDate());
        meetingsDTO.setCreatorName(meetings.getCreatorId().getName());
        return meetingsDTO;
    }
}