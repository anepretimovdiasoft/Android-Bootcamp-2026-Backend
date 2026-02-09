package ru.sicampus.bootcamp2026.mapper;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponseDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationMapper {
    public InvitationResponseDTO convertToDto(Invitation invitation) {
        return new InvitationResponseDTO(
                invitation.getId(),
                invitation.getStatus(),
                invitation.getCreatedAt(),
                invitation.getRespondedAt(),
                MeetingMapper.convertToDto(invitation.getMeeting())
        );
    }
}
