package ru.sicampus.bootcamp2026.util;

import ru.sicampus.bootcamp2026.dto.invitationsDTO;
import ru.sicampus.bootcamp2026.entity.invitations;

public class invitationsMapper {

    public static invitationsDTO toDTO(invitations invitation) {
        if (invitation == null) return null;

        invitationsDTO dto = new invitationsDTO();
        dto.setId(invitation.getId());
        dto.setMeetId(invitation.getMeet().getId());
        dto.setUserId(invitation.getUser().getId());
        dto.setMeetTitle(invitation.getMeet().getTitle());
        dto.setUserName(invitation.getUser().getFullName());
        dto.setMeetDate(invitation.getMeet().getMeetDate().toString());
        dto.setMeetTime(invitation.getMeet().getMeetTime().toString());

        return dto;
    }
}