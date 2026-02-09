package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.*;

import java.util.List;

public interface InvitationService {
    InvitationDTO createInvitation(InvitationCreateDTO dto, String username);
    List<InvitationDTO> createInvitationsBatch(InvitationCreateBatchDTO dto, String username);
    InvitationDTO answerInvitation(InvitationAnswerDTO dto, String username);
    List<InvitationMeetingDTO> getActiveInvitations(String username);
}
