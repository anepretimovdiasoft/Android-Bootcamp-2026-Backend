package ru.sicampus.bootcamp2026.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dtos.InvitationDto;
import ru.sicampus.bootcamp2026.services.InvitationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationDto createInvitation(@RequestBody InvitationDto invitationDto) {
        return invitationService.createInvitation(invitationDto);
    }

    @GetMapping("/{id}")
    public InvitationDto getInvitationById(@PathVariable Long id) {
        return invitationService.getInvitationById(id);
    }

    @GetMapping("/user/{userId}")
    public List<InvitationDto> getInvitationsByUserId(@PathVariable Long userId) {
        return invitationService.getInvitationsByUserId(userId);
    }

    @GetMapping("/meeting/{meetingId}")
    public List<InvitationDto> getInvitationsByMeetingId(@PathVariable Long meetingId) {
        return invitationService.getInvitationsByMeetingId(meetingId);
    }

    @PatchMapping("/{id}/status")
    public InvitationDto updateInvitationStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        return invitationService.updateInvitationStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
    }
}
