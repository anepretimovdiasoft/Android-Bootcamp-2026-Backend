package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;

@UtilityClass
public class MeetingMapper {
    public MeetingDTO convertToDto(Meeting meeting) {
        MeetingDTO dto = new MeetingDTO();
        dto.setId(meeting.getId());
        dto.setTitle(meeting.getTitle());
        dto.setOrganizerId(meeting.getOrganizer().getId());
        dto.setDate(meeting.getDate());
        dto.setStartHour(meeting.getStartHour());
        dto.setCreatedAt(meeting.getCreatedAt());
        return dto;
    }
}
