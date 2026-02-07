package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationDto;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationMapper {

    public InvitationDto toDto(Invitation entity) {
        var dto = new InvitationDto();

        dto.setId(entity.getId());
        dto.setInviteeId(entity.getInvitee().getId());
        dto.setMeetingId(entity.getMeeting().getId());
        dto.setMeetingType(entity.getMeeting().getType());
        dto.setMeetingStartAt(entity.getMeeting().getStartAt());
        dto.setMeetingEndAt(entity.getMeeting().getEndAt());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}
