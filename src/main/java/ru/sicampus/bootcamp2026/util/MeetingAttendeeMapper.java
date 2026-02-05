package ru.sicampus.bootcamp2026.util;


import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingAttendeeDto;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendees;


@UtilityClass
public class MeetingAttendeeMapper {

    public MeetingAttendeeDto toDto(MeetingAttendees attendee) {
        MeetingAttendeeDto dto = new MeetingAttendeeDto();
        dto.setUserId(attendee.getUser().getId());
        dto.setUsername(attendee.getUser().getUsername());
        dto.setStatus(attendee.getStatus());
        return dto;
    }
}
