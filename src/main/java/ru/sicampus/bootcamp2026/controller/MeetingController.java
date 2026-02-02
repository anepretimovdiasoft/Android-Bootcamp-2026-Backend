package ru.sicampus.bootcamp2026.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.MeetingService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping("/api/meetings")
    public ResponseEntity<ApiResponseDTO> create(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @RequestBody MeetingCreateDTO req) {
        meetingService.createMeeting(userId, req);
        return ResponseEntity.ok(new ApiResponseDTO("Встреча создана"));
    }

    @GetMapping("/api/meetings/{id}")
    public ResponseEntity<MeetingInfoDTO> getInfo(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingInfo(id));
    }

    @GetMapping("/api/schedule")
    public ResponseEntity<List<ScheduleEntryDTO>> getSchedule(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(meetingService.getSchedule(userId, startDate, endDate));
    }
}