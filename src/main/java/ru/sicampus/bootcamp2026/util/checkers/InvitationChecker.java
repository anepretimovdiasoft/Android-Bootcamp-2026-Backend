package ru.sicampus.bootcamp2026.util.checkers;

import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;

public class InvitationChecker {
    public static Invitation checkInvitation(InvitationRepository invitationRepository, Long id) {
        return invitationRepository
                .findById(id)
                .orElseThrow(InvitationNotFoundException::new);
    }
}
