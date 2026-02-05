package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PostMapping
    public ResponseEntity<MeetingDTO> createMeeting(@RequestBody MeetingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(dto));
    }

    @GetMapping("/id={id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @GetMapping
    public ResponseEntity<List<MeetingDTO>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<MeetingDTO>> getAllMeetingsPaginated(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date", "startTime"));
        return ResponseEntity.ok(meetingService.getAllMeetingsPaginated(pageable));
    }

    @GetMapping("/title={title}")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsByTitle(@PathVariable String title) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByTitle(title));
    }

    @GetMapping("/invited/{id}")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsByInvitedUserId(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByInvitedUserId(id));
    }

    @GetMapping("/planned/{id}")
    public ResponseEntity<List<MeetingDTO>> getAllPlannedMeetingsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getAllPlannedMeetingsByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDTO> updateMeeting(@PathVariable Long id, @RequestBody MeetingDTO dto) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }
}
