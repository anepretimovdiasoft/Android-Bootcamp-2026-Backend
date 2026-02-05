package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDto;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;

import java.util.List;

public interface InvitationService {
    List<InvitationDto> getMyInvitations(Long currentUserId);

    void respondToInvitation(Long invitationId, InvitationStatus status, Long currentUserId);

    List<InvitationDto> getMeetingInvitations(Long meetingId, Long organizerId);

}
