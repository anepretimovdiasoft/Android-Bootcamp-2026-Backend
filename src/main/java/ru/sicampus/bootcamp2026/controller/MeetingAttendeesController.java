package ru.sicampus.bootcamp2026.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingAttendeeDto;
import ru.sicampus.bootcamp2026.dto.MeetingsDto;
import ru.sicampus.bootcamp2026.model.enums.UserStatus;
import ru.sicampus.bootcamp2026.service.MeetingAttendeesService;

import java.util.List;


@RequestMapping("/attendees")
@RequiredArgsConstructor
@RestController
public class MeetingAttendeesController {

    private final MeetingAttendeesService  meetingAttendeesService;

    @GetMapping("/users/{meetingId}")
    public ResponseEntity<List<MeetingAttendeeDto>> getAttendees(
            @PathVariable("meetingId") Long id)
    {
        return ResponseEntity.ok(
                meetingAttendeesService.getAttendeesByMeetingId(id)
        );
    }

    @PostMapping("{meetingId}/respond/{userId}")
    public ResponseEntity<Void> respondToInvitation(

            @PathVariable("meetingId") Long meetingId,
            @PathVariable("userId") Long userId,
            @RequestParam UserStatus status)
    {
        meetingAttendeesService.respondToInvitation(meetingId, userId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invitations/{userId}")
    public ResponseEntity<List<MeetingsDto>> getUserInvitations(@PathVariable Long userId) {
        List<MeetingsDto> invitations = meetingAttendeesService.getMeetingsForUser(userId);
        return ResponseEntity.ok(invitations);
    }
}
