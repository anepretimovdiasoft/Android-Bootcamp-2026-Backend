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

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable(name = "id") Long id) {
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

    @GetMapping("/by_title/{title}")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsByTitle(@PathVariable(name = "title") String title) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByTitle(title));
    }

    @GetMapping("/invited/{userId}")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsByInvitedUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByInvitedUserId(userId));
    }

    @GetMapping("/invited/paginated/{userId}")
    public ResponseEntity<Page<MeetingDTO>> getAllMeetingsByInvitedUserIdPaginated(@PathVariable(name = "userId") Long userId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(meetingService.getAllMeetingsByInvitedUserIdPaginated(userId, pageable));
    }

    @GetMapping("/planned/{userId}")
    public ResponseEntity<List<MeetingDTO>> getAllPlannedMeetingsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(meetingService.getAllPlannedMeetingsByUserId(userId));
    }

    @GetMapping("/planned/paginated/{userId}")
    public ResponseEntity<Page<MeetingDTO>> getAllPlannedMeetingsByUserIdPaginated(@PathVariable(name = "userId") Long userId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(meetingService.getAllPlannedMeetingsByUserIdPaginated(userId, pageable));
    }

    @GetMapping("/planned/paginated/by_date/{userId}")
    public ResponseEntity<Page<MeetingDTO>> getAllPlannedMeetingsByUserIdAndDatePaginated(
            @PathVariable(name = "userId") Long userId,
            @RequestParam String dateString,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(meetingService.getAllPlannedMeetingsByUserIdAndDatePaginated(userId, dateString, pageable));
    }

    @GetMapping("/planned/paginated/by_date_period/{userId}")
    public ResponseEntity<Page<MeetingDTO>> getAllPlannedMeetingsByUserIdAndDatePeriodPaginated(
            @PathVariable Long userId,
            @RequestParam String datePeriodStart,
            @RequestParam String datePeriodEnd,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date", "startTime"));

        return ResponseEntity.ok(meetingService.getAllPlannedMeetingsByUserIdAndDatePeriodPaginated(userId, datePeriodStart, datePeriodEnd, pageable));
    }

    @GetMapping("/invited/paginated/by_date/{userId}")
    public ResponseEntity<Page<MeetingDTO>> getAllMeetingsByInvitedUserIdAndDatePaginated(
            @PathVariable(name = "userId") Long userId,
            @RequestParam String dateString,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(meetingService.getAllMeetingsByInvitedUserIdAndDatePaginated(userId, dateString, pageable));
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
