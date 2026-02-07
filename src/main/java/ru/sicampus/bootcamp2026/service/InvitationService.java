package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {
    InvitationDTO respondToInvitation(Long invitationId, String response);

    List<InvitationDTO> getPersonInvitations(Long PersonId);
}