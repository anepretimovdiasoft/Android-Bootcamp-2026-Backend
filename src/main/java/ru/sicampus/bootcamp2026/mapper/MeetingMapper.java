package ru.sicampus.bootcamp2026.mapper;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;

@UtilityClass
public class MeetingMapper {
    public MeetingResponseDTO convertToDto(Meeting meeting) {
        return new MeetingResponseDTO(
                meeting.getId(),
                UserMapper.convertToDto(meeting.getOrganizer()),
                meeting.getTitle(),
                meeting.getDescription(),
                meeting.getTimeStart(),
                meeting.getTimeEnd(),
                meeting.getCreatedAt(),
                meeting.getUpdatedAt()
        );
    }
}
