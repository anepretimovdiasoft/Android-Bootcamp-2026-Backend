package ru.sicampus.bootcamp2026.util;

import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;

public class MeetingMapper {

    public static MeetingDTO toDto(Meeting meeting) {
        if (meeting == null) return null;

        Long organizerId = meeting.getOrganizer() != null ?
                meeting.getOrganizer().getId() : null;

        return new MeetingDTO(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getDescription(),
                meeting.getDate(),
                meeting.getStartTime(),
                meeting.getDurationHours(),
                organizerId
        );
    }
}