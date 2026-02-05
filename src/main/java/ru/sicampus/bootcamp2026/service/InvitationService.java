package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;

import java.util.List;

public interface InvitationService {
    InvitationDTO createInvitation(InvitationCreateDTO invitationCreateDTO, String username);
    InvitationDTO answerInvitation(InvitationAnswerDTO invitationAnswerDTO, String username);
    List<InvitationMeetingDTO> getActiveInvitations(String username);
}
