package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.entity.Meetings;

@UtilityClass
public class MeetingsMapper {
    public MeetingsDTO convertToDto(Meetings meetings){
        MeetingsDTO meetingsDTO = new MeetingsDTO();
        meetingsDTO.setId(meetings.getId());
        meetingsDTO.setTopic(meetings.getTopic());
        meetingsDTO.setDescription(meetings.getDescription());
        meetingsDTO.setCreator(meetings.getCreator());
        meetingsDTO.setDate(meetings.getDate());
        meetingsDTO.setTimeStart(meetings.getTimeStart());
        meetingsDTO.setDuration(meetings.getDuration());
        meetingsDTO.setPlace(meetings.getPlace());
        meetingsDTO.setStatus(meetings.getStatus());
        return meetingsDTO;
    }
}
