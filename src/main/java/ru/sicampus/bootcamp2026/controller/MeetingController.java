package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<MeetingDTO>> getAll() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @PostMapping
    public ResponseEntity<MeetingDTO> create(@RequestBody MeetingDTO dto) {
        MeetingDTO created = meetingService.createMeeting(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDTO> update(@PathVariable Long id, @RequestBody MeetingDTO dto) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }
}
