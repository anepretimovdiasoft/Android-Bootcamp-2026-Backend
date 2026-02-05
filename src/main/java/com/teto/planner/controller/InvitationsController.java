package com.teto.planner.controller;

import com.teto.planner.dto.InvitationResponseRequest;
import com.teto.planner.dto.InvitationsResponse;
import com.teto.planner.dto.MeetingParticipantDto;
import com.teto.planner.entity.ParticipantStatus;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.service.CurrentUserService;
import com.teto.planner.service.InvitationService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationsController {
    private final InvitationService invitationService;
    private final CurrentUserService currentUserService;

    public InvitationsController(InvitationService invitationService, CurrentUserService currentUserService) {
        this.invitationService = invitationService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public InvitationsResponse listInvitations(
            @RequestParam(value = "status", required = false) ParticipantStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "50") int size
    ) {
        UserEntity currentUser = currentUserService.getCurrentUser();
        return invitationService.listInvitations(currentUser, status, page, size);
    }

    @PostMapping("/{meetingId}/response")
    public ResponseEntity<MeetingParticipantDto> respond(
            @PathVariable UUID meetingId,
            @Valid @RequestBody InvitationResponseRequest request
    ) {
        UserEntity currentUser = currentUserService.getCurrentUser();
        MeetingParticipantDto updated = invitationService.respond(currentUser, meetingId, request.status());
        return ResponseEntity.ok(updated);
    }
}
