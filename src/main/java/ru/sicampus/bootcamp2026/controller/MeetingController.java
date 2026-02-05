package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.exception.MeetingException;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping("/")
    public ResponseEntity<MeetingResponseDTO> createMeeting(@Valid @RequestBody MeetingCreateDTO dto) throws MeetingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(meetingService.createMeeting(dto));
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<MeetingResponseDTO>> getSchedule() {
        return ResponseEntity.ok(meetingService.getSchedule());
    }
}
