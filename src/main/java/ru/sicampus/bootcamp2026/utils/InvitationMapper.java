package ru.sicampus.bootcamp2026.utils;

import ru.sicampus.bootcamp2026.dtos.InvitationDto;
import ru.sicampus.bootcamp2026.entities.Invitation;

public class InvitationMapper {
    public static InvitationDto convertToDto(Invitation invitation) {
        InvitationDto dto = new InvitationDto();
        dto.setId(invitation.getId());
        dto.setStatus(invitation.getStatus());
        dto.setUserId(invitation.getUser().getId());
        dto.setUserName(invitation.getUser().getName());
        dto.setMeetingId(invitation.getMeeting().getId());
        dto.setMeetingTitle(invitation.getMeeting().getTitle());
        return dto;
    }
}
