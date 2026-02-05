package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.InvitationRequest;
import ru.examle.edu.dto.InvitationUpdateRequest;
import ru.examle.edu.dto.InvitationDto;
import ru.examle.edu.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<InvitationDto>> getAllInvitations() {
        return ResponseEntity.ok(invitationService.getAllInvitations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationDto> getInvitationById(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.getInvitationById(id));
    }

    @PostMapping
    public ResponseEntity<InvitationDto> createInvitation(@RequestBody InvitationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invitationService.createInvitation(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InvitationDto> updateInvitationStatus(
            @PathVariable Long id,
            @RequestBody InvitationUpdateRequest request) {
        return ResponseEntity.ok(invitationService.updateInvitationStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<InvitationDto>> getInvitationsByPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(invitationService.getInvitationsByPerson(personId));
    }

    @GetMapping("/meeting/{meetingId}")
    public ResponseEntity<List<InvitationDto>> getInvitationsByMeeting(@PathVariable Long meetingId) {
        return ResponseEntity.ok(invitationService.getInvitationsByMeeting(meetingId));
    }
}