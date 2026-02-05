package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.fromApp.NewMeetingDTO;
import ru.sicampus.bootcamp2026.dto.toApp.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.toApp.TimeSlotDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping()
    public ResponseEntity<MeetingDTO> createMeeting(@RequestBody NewMeetingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MeetingDTO> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<MeetingDTO>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @GetMapping("/booked")
    public ResponseEntity<List<TimeSlotDTO>> getBookedSlots (
            @RequestParam(name = "date") String date
    ) {
        return ResponseEntity.ok(meetingService.getBookedSlotsByDate(date));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<MeetingDTO>> getUserMeetings(
            @PathVariable Long id,
            @RequestParam(name = "start_date", required = false) String startDate,
            @RequestParam(name = "end_date", required = false) String endDate
    ) {
        return ResponseEntity.ok(meetingService.getUserMeetings(id, startDate, endDate));
    }
}
