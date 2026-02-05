package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationDto;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    // Временно: демо-пользователь = 1L
    private static final Long CURRENT_USER_ID = 1L;

    /**
     * Получить список активных приглашений текущего пользователя.
     */
    @GetMapping("/v1/{id}")
    public ResponseEntity<List<InvitationDto>> getMyInvitations(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.getMyInvitations(id));
    }

    /**
     * Ответить на приглашение (ACCEPTED / DECLINED).
     */
    @PatchMapping("/v1/{invitationId}")
    public ResponseEntity<Void> respondToInvitation(
            @PathVariable Long invitationId,
            @RequestParam InvitationStatus status) {
        invitationService.respondToInvitation(invitationId, status, CURRENT_USER_ID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получить статусы всех приглашённых на встречу (для организатора).
     */
    @GetMapping("/v1/meeting/{meetingId}")
    public ResponseEntity<List<InvitationDto>> getMeetingInvitations(@PathVariable Long meetingId) {
        return ResponseEntity.ok(invitationService.getMeetingInvitations(meetingId, CURRENT_USER_ID));
    }
}