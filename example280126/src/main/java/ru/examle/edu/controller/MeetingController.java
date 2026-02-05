package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.MeetingRequest;
import ru.examle.edu.dto.MeetingDto;
import ru.examle.edu.service.MeetingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<MeetingDto>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @PostMapping
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody MeetingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(meetingService.createMeeting(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDto> updateMeeting(
            @PathVariable Long id,
            @RequestBody MeetingRequest request) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<MeetingDto>> getMeetingsByOrganizer(@PathVariable Long organizerId) {
        return ResponseEntity.ok(meetingService.getMeetingsByOrganizer(organizerId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<MeetingDto>> getMeetingsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(meetingService.getMeetingsByDate(date));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<MeetingDto>> getMeetingsForPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(meetingService.getMeetingsForPerson(personId));
    }
}