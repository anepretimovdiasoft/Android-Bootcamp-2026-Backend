package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.service.MeetingsService;

import java.util.List;

@RestController
@RequestMapping("api/meetings")
@RequiredArgsConstructor
public class MeetingsController {
    private final MeetingsService meetingsService;

    @GetMapping
    public List<MeetingsDTO> getAllMeetings() {
        return meetingsService.getAllMeetings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingsDTO> getMeetingsById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingsService.getMeetingsById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MeetingsDTO> createMeetings(@RequestBody MeetingsDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingsService.createMeetings(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingsDTO> updateMeetings(@PathVariable Long id, @RequestBody MeetingsDTO dto) {
        return ResponseEntity.ok(meetingsService.updateMeetings(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeetings(@PathVariable Long id) {
        meetingsService.deleteMeetings(id);
        return ResponseEntity.noContent().build();
    }
}
