package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;

public interface InvitationsService {
    InvitationsDTO getInvitationById(long id);

    InvitationsDTO createInvitation(InvitationsDTO invitationsDTO);

    InvitationsDTO updateInvitation(long id, InvitationsDTO invitationsDTO);

    void deleteInvitation(long id);
}