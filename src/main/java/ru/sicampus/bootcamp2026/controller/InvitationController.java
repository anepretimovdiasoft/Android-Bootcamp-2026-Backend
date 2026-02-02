package ru.sicampus.bootcamp2026.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.MeetingService;
import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getList(@RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        return ResponseEntity.ok(meetingService.getInvitations(userId));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<ApiResponseDTO> respond(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @PathVariable Long id, @RequestBody InvitationResponseDTO req) {
        meetingService.respondToInvitation(userId, id, req.getStatus());
        return ResponseEntity.ok(new ApiResponseDTO("Ответ сохранен"));
    }
}