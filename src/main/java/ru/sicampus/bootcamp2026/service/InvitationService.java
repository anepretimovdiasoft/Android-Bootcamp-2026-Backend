package ru.sicampus.bootcamp2026.service;

import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponse;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;

import java.util.List;
import java.util.UUID;

public interface InvitationService {

    /**
     * Получение списка приглашений пользователя
     */
    List<InvitationResponse> getUserInvitations(UUID userId);

    /**
     * Получение приглашения по ID
     */
    InvitationResponse getInvitationById(UUID userId, UUID invitationId);

    /**
     * Ответ на приглашение (принять/отклонить)
     */
    InvitationResponse respondToInvitation(UUID userId, UUID meetingId, ParticipantStatus status);

    /**
     * Отмена приглашения организатором
     */
    void cancelInvitation(UUID organizerId, UUID meetingId, UUID participantId);
}
