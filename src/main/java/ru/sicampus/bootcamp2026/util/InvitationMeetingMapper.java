package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationMeetingMapper {
    public InvitationMeetingDTO convertToDTO(Invitation invitation) {
        InvitationMeetingDTO invitationMeetingDTO = new InvitationMeetingDTO();

        invitationMeetingDTO.setId(invitation.getId());
        invitationMeetingDTO.setStatus(invitation.getStatus());

        invitationMeetingDTO.setMessage(invitation.getMessage());
        invitationMeetingDTO.setMeeting(MeetingMapper.convertToDTO(invitation.getMeeting()));

        return invitationMeetingDTO;
    }
}
