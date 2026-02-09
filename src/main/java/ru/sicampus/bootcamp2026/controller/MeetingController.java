package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.CreateMeetingRequestDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.PersonDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public List<MeetingDTO> getMeetings(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        if (date == null) {
            return meetingService.getAllMeetings();
        }

        return meetingService.getMeetingsByDate(date);
    }

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