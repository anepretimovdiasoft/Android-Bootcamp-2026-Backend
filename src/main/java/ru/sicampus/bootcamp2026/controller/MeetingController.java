package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserService userService;

    @GetMapping("/v1/{meetingId}")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable Long meetingId) {
        return ResponseEntity.ok(meetingService.getMeetingById(meetingId));
    }

    @PostMapping("/v1")
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody MeetingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(meetingService.createMeeting(dto));
    }

    @PutMapping("/v1/{meetingId}")
    public ResponseEntity<MeetingDto> updateMeeting(
            Authentication authentication,
            @PathVariable Long meetingId,
            @RequestBody MeetingDto dto) {
        String login = authentication.getName();
        Long userId = userService.getUserByLogin(login).getId();
        return ResponseEntity.ok(meetingService.updateMeeting(meetingId, dto, userId));
    }

    @DeleteMapping("/v1/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(
            Authentication authentication,
            @PathVariable Long meetingId
    ) {
        String login = authentication.getName();
        Long userId = userService.getUserByLogin(login).getId();
        meetingService.deleteMeeting(meetingId, userId);
        return ResponseEntity.noContent().build();
    }
}

