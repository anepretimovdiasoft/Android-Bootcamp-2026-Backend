package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponse;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    /*
        TODO: Внедрить репозиторий позже
    */

    @Override
    public List<InvitationResponse> getUserInvitations(UUID userId) {
        /*
            TODO: Реализовать получение списка приглашений пользователя
        */
        throw new UnsupportedOperationException("Метод getUserInvitations еще не реализован");
    }

    @Override
    public InvitationResponse getInvitationById(UUID userId, UUID meetingId) {
        /*
            TODO: Реализовать получение приглашения по ID
        */
        throw new UnsupportedOperationException("Метод getInvitationById еще не реализован");
    }

    @Override
    public InvitationResponse respondToInvitation(UUID userId, UUID meetingId, ParticipantStatus status) {
        /*
            TODO: Реализовать ответ на приглашение
        */
        throw new UnsupportedOperationException("Метод respondToInvitation еще не реализован");
    }

    @Override
    public void cancelInvitation(UUID organizerId, UUID meetingId, UUID participantId) {
        /*
            TODO: Реализовать отмену приглашения
        */
        throw new UnsupportedOperationException("Метод cancelInvitation еще не реализован");
    }
}