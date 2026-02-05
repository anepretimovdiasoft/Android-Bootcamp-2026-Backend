package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.InvitationDTO;
import ru.examle.edu.entity.Invitation;

@Component
public class InvitationMapper {
    public InvitationDTO toDTO(Invitation invitation) {
        if (invitation == null) return null;
        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitation.getId());
        dto.setMeetingId(invitation.getMeetingId());
        dto.setUserId(invitation.getUserId());
        dto.setResponseStatus(invitation.getResponseStatus());
        dto.setResponseComment(invitation.getResponseComment());
        dto.setRequired(invitation.isRequired());
        dto.setRespondedAt(invitation.getRespondedAt());
        dto.setCreatedAt(invitation.getCreatedAt());
        return dto;
    }

    public Invitation toEntity(InvitationDTO dto) {
        if (dto == null) return null;
        Invitation invitation = new Invitation();
        if (dto.getId() != null) invitation.setId(dto.getId());
        if (dto.getMeetingId() != null) invitation.setMeetingId(dto.getMeetingId());
        if (dto.getUserId() != null) invitation.setUserId(dto.getUserId());
        if (dto.getResponseStatus() != null) invitation.setResponseStatus(dto.getResponseStatus());
        invitation.setResponseComment(dto.getResponseComment());
        invitation.setRequired(dto.isRequired());
        invitation.setRespondedAt(dto.getRespondedAt());
        return invitation;
    }
}
