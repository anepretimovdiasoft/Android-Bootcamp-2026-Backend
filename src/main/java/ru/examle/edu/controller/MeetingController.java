package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.MeetingDTO;
import ru.examle.edu.service.MeetingService;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    public Page<MeetingDTO> getAllMeetings(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return meetingService.getAllMeetings(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public MeetingDTO getMeetingById(@PathVariable Long id) {
        return meetingService.getMeetingById(id);
    }

    @PostMapping
    public MeetingDTO createMeeting(@RequestBody MeetingDTO meetingDTO) {
        return meetingService.createMeeting(meetingDTO);
    }

    @PutMapping("/{id}")
    public MeetingDTO updateMeeting(@PathVariable Long id, @RequestBody MeetingDTO meetingDTO) {
        return meetingService.updateMeeting(id, meetingDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
    }
}
