package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.service.MeetingService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/v1/{id}")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @PostMapping("/v1")
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody MeetingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(meetingService.createMeeting(dto));
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<MeetingDto> updateMeeting(@PathVariable Long id, @RequestBody MeetingDto dto) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, dto));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }
}

