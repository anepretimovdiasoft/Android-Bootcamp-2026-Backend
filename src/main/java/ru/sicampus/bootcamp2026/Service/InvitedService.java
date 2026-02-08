package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.EmployeeNamesRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetUpdateInvitedRequest;
import ru.sicampus.bootcamp2026.Dto.response.Invited.InvitedResponse;
import ru.sicampus.bootcamp2026.Entity.Invitations;
import ru.sicampus.bootcamp2026.Entity.Invited;

import java.util.List;

@Service
public interface InvitedService {
    List<Invited> createdInviteds(List<EmployeeNamesRequest> invitedList, Invitations invitations);
    InvitedResponse getInvited(int page,int size);
    void updateInvited(GetUpdateInvitedRequest dto);
}
