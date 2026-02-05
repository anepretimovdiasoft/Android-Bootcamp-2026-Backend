package ru.sicampus.bootcamp2026.controller;

import ru.sicampus.bootcamp2026.dto.cinvitationsDTO;
import ru.sicampus.bootcamp2026.dto.invitationsDTO;
import ru.sicampus.bootcamp2026.service.invitationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationsController {
    private final invitationsService invitationsService;

    @PostMapping
    public ResponseEntity<invitationsDTO> createInvitation(@Valid @RequestBody cinvitationsDTO createDTO) {
        invitationsDTO invitationDTO = invitationsService.createInvitation(createDTO);
        return new ResponseEntity<>(invitationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<invitationsDTO> getInvitationById(@PathVariable Long id) {
        invitationsDTO invitationDTO = invitationsService.getInvitationById(id);
        return ResponseEntity.ok(invitationDTO);
    }

    @GetMapping
    public ResponseEntity<List<invitationsDTO>> getAllInvitations() {
        List<invitationsDTO> invitations = invitationsService.getAllInvitations();
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<invitationsDTO>> getInvitationsByUser(@PathVariable Long userId) {
        List<invitationsDTO> invitations = invitationsService.getInvitationsByUserId(userId);
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/meet/{meetId}")
    public ResponseEntity<List<invitationsDTO>> getInvitationsByMeet(@PathVariable Long meetId) {
        List<invitationsDTO> invitations = invitationsService.getInvitationsByMeetId(meetId);
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/meet/{meetId}/count")
    public ResponseEntity<Long> countInvitationsByMeet(@PathVariable Long meetId) {
        long count = invitationsService.countInvitationsByMeetId(meetId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        invitationsService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}