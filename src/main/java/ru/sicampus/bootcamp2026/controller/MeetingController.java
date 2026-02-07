package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.CreateMeetingRequestDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public MeetingDTO createMeeting(@RequestBody CreateMeetingRequestDTO request) {
        return meetingService.createMeeting(request);
    }

    @GetMapping("/{id}")
    public MeetingDTO getMeeting(@PathVariable Long id) {
        return meetingService.getMeeting(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
    }

    @GetMapping("/person/{personId}")
    public List<MeetingDTO> getPersonMeetings(@PathVariable Long personId) {
        return meetingService.getPersonMeetings(personId);
    }
}