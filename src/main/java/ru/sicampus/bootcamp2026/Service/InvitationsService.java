package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetInvitationsCreatedRequest;

@Service
public interface InvitationsService {
    void createdInvitations(GetInvitationsCreatedRequest dto);
}
