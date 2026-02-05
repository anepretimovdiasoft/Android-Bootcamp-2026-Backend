package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getAll() {
        return ResponseEntity.ok(invitationService.getAllInvitations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.getInvitationById(id));
    }

    @PostMapping
    public ResponseEntity<InvitationDTO> create(@RequestBody InvitationDTO dto) {
        InvitationDTO created = invitationService.createInvitation(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationDTO> update(@PathVariable Long id, @RequestBody InvitationDTO dto) {
        return ResponseEntity.ok(invitationService.updateInvitation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}
