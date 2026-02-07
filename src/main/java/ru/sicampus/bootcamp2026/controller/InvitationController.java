package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.InvitationActionRequest;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponse;
import ru.sicampus.bootcamp2026.model.CustomUserDetails;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    /**
     * Получение списка активных приглашений текущего пользователя
     */
    @GetMapping
    public ResponseEntity<List<InvitationResponse>> getInvitations(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        List<InvitationResponse> invitations =
                invitationService.getUserInvitations(currentUser.user().getId());
        return ResponseEntity.ok(invitations);
    }

    /**
     * Получение приглашения по ID встречи, детали приглашения
     */
    @GetMapping("/{meetingId}")
    public ResponseEntity<InvitationResponse> getInvitationDetails(
            @PathVariable UUID meetingId,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        InvitationResponse invitation = invitationService.getInvitationById(
                currentUser.user().getId(),
                meetingId
        );
        return ResponseEntity.ok(invitation);
    }

    /**
     * Принять или отклонить приглашение
     */
    @PutMapping("/{meetingId}/respond")
    public ResponseEntity<InvitationResponse> respondToInvitation(
            @PathVariable UUID meetingId,
            @Valid @RequestBody InvitationActionRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        InvitationResponse response = invitationService.respondToInvitation(
                currentUser.user().getId(),
                meetingId,
                request.getStatus()
        );
        return ResponseEntity.ok(response);
    }
}
