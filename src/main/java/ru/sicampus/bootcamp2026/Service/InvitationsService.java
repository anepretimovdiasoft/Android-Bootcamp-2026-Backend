package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetInvitationsCreatedRequest;
import ru.sicampus.bootcamp2026.Dto.response.Invited.InvitedResponse;
import ru.sicampus.bootcamp2026.Entity.Invitations;

import java.util.List;
import java.util.Map;

@Service
public interface InvitationsService {
    void createdInvitations(GetInvitationsCreatedRequest dto);

}
