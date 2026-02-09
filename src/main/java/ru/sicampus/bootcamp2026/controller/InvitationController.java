package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/invitation/")
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getAllInvitation(@AuthenticationPrincipal Users user) {
        return ResponseEntity.ok(invitationService.getAllInvitation(user));
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> acceptInvitation(@PathVariable Long id, @AuthenticationPrincipal Users user) {
        invitationService.acceptInvitation(user, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectInvitation(@PathVariable Long id, @AuthenticationPrincipal Users user) {
        invitationService.rejectInvitation(user, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send")
    public ResponseEntity<InvitationDTO> sendInvitation(@RequestBody InvitationCreateDTO dto, @AuthenticationPrincipal Users user) {
        return ResponseEntity.ok(invitationService.sendInvitation(user, dto.getMeetingId(), dto.getEmailTarget()));
    }
}
