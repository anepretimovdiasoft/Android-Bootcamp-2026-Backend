package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.MeetingDTO;
import ru.examle.edu.entity.Meeting;

@Component
public class MeetingMapper {
    public MeetingDTO toDTO(Meeting meeting) {
        if (meeting == null) return null;
        MeetingDTO dto = new MeetingDTO();
        dto.setId(meeting.getId());
        dto.setTitle(meeting.getTitle());
        dto.setDescription(meeting.getDescription());
        dto.setOrganizerId(meeting.getOrganizerId());
        dto.setRoomId(meeting.getRoomId());
        dto.setStartTime(meeting.getStartTime());
        dto.setEndTime(meeting.getEndTime());
        dto.setRecurrencePattern(meeting.getRecurrencePattern());
        dto.setRecurrenceEndDate(meeting.getRecurrenceEndDate());
        dto.setMeetingPriority(meeting.getMeetingPriority());
        dto.setStatus(meeting.getStatus());
        dto.setCreatedAt(meeting.getCreatedAt());
        dto.setUpdatedAt(meeting.getUpdatedAt());
        return dto;
    }

    public Meeting toEntity(MeetingDTO dto) {
        if (dto == null) return null;
        Meeting meeting = new Meeting();
        if (dto.getId() != null) meeting.setId(dto.getId());
        meeting.setTitle(dto.getTitle());
        meeting.setDescription(dto.getDescription());
        if (dto.getOrganizerId() != null) meeting.setOrganizerId(dto.getOrganizerId());
        meeting.setRoomId(dto.getRoomId());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());
        meeting.setRecurrencePattern(dto.getRecurrencePattern());
        meeting.setRecurrenceEndDate(dto.getRecurrenceEndDate());
        if (dto.getMeetingPriority() != null) meeting.setMeetingPriority(dto.getMeetingPriority());
        if (dto.getStatus() != null) meeting.setStatus(dto.getStatus());
        return meeting;
    }
}
