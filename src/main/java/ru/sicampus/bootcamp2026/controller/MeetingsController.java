package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.service.MeetingsService;
import ru.sicampus.bootcamp2026.service.UsersService;

@RestController
@RequestMapping("api/meetings")
@RequiredArgsConstructor
public class MeetingsController {
    private final MeetingsService meetingsService;
    private final UsersService usersService;

    @GetMapping("/{id}")
    public ResponseEntity<MeetingsDTO> getMeeting(@PathVariable long id) {
        return ResponseEntity.ok(meetingsService.getMeetingById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<MeetingsDTO> createMeeting(@RequestBody MeetingsDTO dto) {
        return ResponseEntity.ok(meetingsService.createMeeting(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingsDTO> updateMeeting(@PathVariable long id, @RequestBody MeetingsDTO dto) {
        return ResponseEntity.ok(meetingsService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable long id) {
        meetingsService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<MeetingsDTO>> getAllMeetingsPaginate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(meetingsService.getAllPaginated(page, size));
    }
}