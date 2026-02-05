package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MeetingMapper {
    public MeetingDTO convertToDto(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setId(meeting.getId());
        meetingDTO.setTitle(meeting.getTitle());
        meetingDTO.setDate(meeting.getDate());
        meetingDTO.setStartTime(meeting.getStartTime());
        meetingDTO.setEndTime(meeting.getEndTime());
        meetingDTO.setCreatorId(meeting.getCreator().getId());

        List<Long> invitedUsers = new ArrayList<>();
        meeting.getInvites().forEach(invite -> invitedUsers.add(invite.getInvitedUser().getId()));
        meetingDTO.setInvitedUserIds(invitedUsers);

        return meetingDTO;
    }
}
