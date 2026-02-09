package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingInputDTO;
import ru.sicampus.bootcamp2026.dto.MemberDTO;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/meeting/")
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<MeetingDTO>> getAllMeeting() {
        return ResponseEntity.ok(meetingService.getAllMeeting());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> getMeetingById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(meetingService.getMeetingById(id));
    }

    @PostMapping("/book")
    public ResponseEntity<MeetingDTO> createMeeting(@AuthenticationPrincipal Users user, @RequestBody MeetingInputDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(user, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDTO> updateMeeting(@PathVariable("id") Long id, @AuthenticationPrincipal Users user, @RequestBody MeetingInputDTO dto) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, user, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<MeetingDTO>> getAllMeetingPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(meetingService.getAllMeetingPaginated(meetingService.buildPage(page, size)));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDTO>> getAllMembersOfMeeting(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getAllMemberOfMeeting(id));
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingAfterNod(@AuthenticationPrincipal Users user) {
        return ResponseEntity.ok(meetingService.getAllMeetingAfterNow(user));
    }
}
