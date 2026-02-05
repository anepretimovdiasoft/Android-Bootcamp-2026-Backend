package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Users;

@UtilityClass
public class MeetingMapper {
    public MeetingDTO convertToDto(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setId(meeting.getId());
        meetingDTO.setDescription(meeting.getDescription());
        meetingDTO.setPlace(meeting.getPlace());
        meetingDTO.setDuration(meeting.getDuration());
        meetingDTO.setStart(meeting.getStart());
        meetingDTO.setTheme(meetingDTO.getTheme());
        meetingDTO.setCreator(UserMapper.convertToDto(meeting.getCreator()));

        return meetingDTO;
    }
}
