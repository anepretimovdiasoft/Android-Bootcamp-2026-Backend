package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingsDto;
import ru.sicampus.bootcamp2026.model.entity.Meetings;


@UtilityClass
public class MeetingMapper {
    public MeetingsDto toDto(Meetings meeting) {

        MeetingsDto dto = new MeetingsDto();
        dto.setId(meeting.getId());
        dto.setTitle(meeting.getTitle());
        dto.setDescription(meeting.getDescription());
        dto.setStartTime(meeting.getStartTime());
        dto.setEndTime(meeting.getEndTime());
        dto.setLocation(meeting.getLocation());
        dto.setStatus(meeting.getStatus());

        // Вложенные объекты
        try {
            dto.setCreator(meeting.getCreator() != null ? UserMapper.toDto(meeting.getCreator()) : null);
        } catch (org.hibernate.LazyInitializationException e) {
            dto.setCreator(null);
        }

        try {
            if (meeting.getAttendees() != null) {
                dto.setAttendees(meeting.getAttendees().stream()
                        .map(MeetingAttendeeMapper::toDto)
                        .toList());
            }
        } catch (org.hibernate.LazyInitializationException e) {
            dto.setAttendees(null);
        }
        return dto;
    }
}
