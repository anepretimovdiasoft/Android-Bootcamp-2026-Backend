package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.util.Set;

@UtilityClass
public class MeetingMapper {
    public MeetingDTO convertToDTO(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();

        meetingDTO.setId(meeting.getId());
        meetingDTO.setName(meeting.getName());
        meetingDTO.setDescription(meeting.getDescription());
        meetingDTO.setStartTime(meeting.getStartTime());
        meetingDTO.setEndTime(meeting.getEndTime());
        meetingDTO.setOwnerName(meeting.getOwner().getName());

        Set<Invitation> inv = meeting.getInvitations(); // For reducing database query counr
        if(inv != null) {
            long total = inv.size();
            long accepted = inv.stream().filter(i -> i.getStatus().equals("ACCEPTED")).toList().size();

            meetingDTO.setTotalInvitations(total);
            meetingDTO.setAcceptedInvitations(accepted);
        }
        else {
            meetingDTO.setTotalInvitations(1L);
            meetingDTO.setAcceptedInvitations(1L);
        }

        return meetingDTO;
    }
}
