package ru.sicampus.bootcamp2026.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dtos.MeetingDto;
import ru.sicampus.bootcamp2026.services.MeetingService;
import ru.sicampus.bootcamp2026.services.impl.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping
    public List<MeetingDto> getAllMeetings() {
        return meetingService.getAllMeetings();
    }

    @GetMapping("/{id}")
    public MeetingDto getMeetingById(@PathVariable Long id) {
        return meetingService.getMeetingById(id);
    }

    @GetMapping("/organizer/{organizerId}")
    public List<MeetingDto> getMeetingsByOrganizerId(@PathVariable Long organizerId) {
        return meetingService.getAllMeetingsByOrganizer(organizerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingDto createMeeting(@RequestBody MeetingDto meetingDto, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return meetingService.createMeeting(meetingDto, userDetails.getId());
    }

    @PutMapping("/{id}")
    public MeetingDto updateMeeting(@PathVariable Long id, @RequestBody MeetingDto meetingDto) {
        return meetingService.updateMeeting(id, meetingDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
    }
}