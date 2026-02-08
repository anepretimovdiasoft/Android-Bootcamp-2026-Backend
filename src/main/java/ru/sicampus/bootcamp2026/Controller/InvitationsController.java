package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetInvitationsCreatedRequest;
import ru.sicampus.bootcamp2026.Service.InvitationsService;

@RestController
@RequestMapping("/api/Invitations")
public class InvitationsController {
    @Autowired
    private InvitationsService invitationsService;
    @PostMapping("/createdIn")
    public ResponseEntity<?> createdInvitations(@Valid @RequestBody GetInvitationsCreatedRequest dto){
        invitationsService.createdInvitations(dto);
        return ResponseEntity.ok().build();
    }
}
