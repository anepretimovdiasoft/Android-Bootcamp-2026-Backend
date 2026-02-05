package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.request.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponseDTO;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationException;
import ru.sicampus.bootcamp2026.exception.MeetingException;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invitation")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService service;

    @GetMapping
    public ResponseEntity<List<InvitationResponseDTO>> getCurrentUserInvitations(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getPendingInvitations(user));
    }

    @PostMapping
    public ResponseEntity<InvitationResponseDTO> createInvitation(@RequestBody InvitationCreateDTO dto) throws MeetingException, InvitationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createInvitation(dto));
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> replyToInvitation(@RequestBody InvitationAnswerDTO dto) throws InvitationException {
        service.replyToInvitation(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable long id) throws InvitationException {
        service.deleteInvitation(id);
        return ResponseEntity.ok().build();
    }
}
