package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;

import java.util.List;

public interface InvitationService {
    List<InvitationDTO> getAllInvitations();

    InvitationDTO respondToInvitation(Long invitationId, String response);

    List<InvitationDTO> getPersonInvitations(Long PersonId);
}