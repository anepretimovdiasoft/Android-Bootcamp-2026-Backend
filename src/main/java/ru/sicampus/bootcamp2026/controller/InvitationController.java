package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationDto;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;
    private final UserService userService;
    /**
     * Получить список активных приглашений текущего пользователя.
     */
    @GetMapping("/v1/me")
    public ResponseEntity<List<InvitationDto>> getMyInvitations(Authentication authentication) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        return ResponseEntity.ok(invitationService.getMyInvitations(id));
    }

    /**
     * Ответить на приглашение (ACCEPTED / DECLINED).
     */
    @PatchMapping("/v1/{invitationId}")
    public ResponseEntity<Void> respondToInvitation(
            Authentication authentication,
            @PathVariable Long invitationId,
            @RequestParam InvitationStatus status) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        invitationService.respondToInvitation(invitationId, status, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получить статусы всех приглашённых на встречу (для организатора).
     */
    @GetMapping("/v1/meeting/{meetingId}")
    public ResponseEntity<List<InvitationDto>> getMeetingInvitations(
            Authentication authentication,
            @PathVariable Long meetingId) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        return ResponseEntity.ok(invitationService.getMeetingInvitations(meetingId, id));
    }
}