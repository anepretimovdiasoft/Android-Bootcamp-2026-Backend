package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.service.InvitationsService;
import ru.sicampus.bootcamp2026.service.UsersService;

@RestController
@RequestMapping("api/invitations")
@RequiredArgsConstructor
public class InvitationsController {
    private final InvitationsService invitationsService;
    private final UsersService usersService;

    @GetMapping("/{id}")
    public ResponseEntity<InvitationsDTO> getInvitation(@PathVariable long id) {
        return ResponseEntity.ok(invitationsService.getInvitationById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<InvitationsDTO> createInvitation(@RequestBody InvitationsDTO invitationsDTO) {
        return ResponseEntity.ok(invitationsService.createInvitation(invitationsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationsDTO> updateInvitation(@PathVariable long id, @RequestBody InvitationsDTO invitationsDTO) {
        return ResponseEntity.ok(invitationsService.updateInvitation(id, invitationsDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable long id) {
        invitationsService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}