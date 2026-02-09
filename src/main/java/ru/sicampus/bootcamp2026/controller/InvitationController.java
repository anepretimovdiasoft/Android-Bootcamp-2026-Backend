package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping()
    public List<InvitationDTO> getInvitations() {
        return invitationService.getAllInvitations();
    }

    @PostMapping("/{id}/respond")
    public InvitationDTO respondToInvitation(
            @PathVariable Long id,
            @RequestParam String response) {
        return invitationService.respondToInvitation(id, response);
    }

    @GetMapping("/person/{personId}")
    public List<InvitationDTO> getPersonInvitations(@PathVariable Long personId) {
        return invitationService.getPersonInvitations(personId);
    }
}