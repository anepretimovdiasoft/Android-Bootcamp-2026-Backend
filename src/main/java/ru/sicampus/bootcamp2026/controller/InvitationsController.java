package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.fromApp.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.dto.toApp.MeetingDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationsController {
    private final MeetingService meetingService;

    @PutMapping()
    public ResponseEntity<MeetingDTO> responseToInvitation(@RequestBody MeetingResponseDTO dto) {
        meetingService.respondToMeeting(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MeetingDTO>> getUserInvitations(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getUserInvitations(id));
    }

}
