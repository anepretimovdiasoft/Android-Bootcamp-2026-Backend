package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.web.dto.invitation.InvitationRespondDto;

import java.util.List;

public interface InvitationService {

    List<Invitation> userInvitations();
    Invitation respondToInvitation(InvitationRespondDto invitationRespondDto);

}
