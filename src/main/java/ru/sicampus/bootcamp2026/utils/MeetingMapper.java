package ru.sicampus.bootcamp2026.utils;

import ru.sicampus.bootcamp2026.dtos.MeetingDto;
import ru.sicampus.bootcamp2026.entities.Meeting;

public class MeetingMapper {
    public static MeetingDto convertToDto(Meeting meeting) {
        MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(meeting.getId());
        meetingDto.setTitle(meeting.getTitle());
        meetingDto.setDescription(meeting.getDescription());
        meetingDto.setStartedAt(meeting.getStartedAt());
        meetingDto.setEndAt(meeting.getEndAt());
        meetingDto.setOrganizerId(meeting.getOrganizer().getId());
        meetingDto.setOrganizerName(meeting.getOrganizer().getName());
        return meetingDto;
    }
}
