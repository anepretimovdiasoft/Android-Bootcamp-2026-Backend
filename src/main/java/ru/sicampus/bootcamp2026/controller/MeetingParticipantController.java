package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingParticipantDTO;
import ru.sicampus.bootcamp2026.service.MeetingParticipantService;

import java.util.List;

@RestController
@RequestMapping("/api/meetings/{meetingId}/participants")
@RequiredArgsConstructor
public class MeetingParticipantController {

    private final MeetingParticipantService participantService;

    @GetMapping
    public List<MeetingParticipantDTO> getParticipants(@PathVariable Long meetingId) {
        return participantService.getParticipantsByMeeting(meetingId);
    }

    @GetMapping("/user/{userId}")
    public List<MeetingParticipantDTO> getParticipantsByUser(@PathVariable Long userId) {
        return participantService.getParticipantsByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingParticipantDTO addParticipant(
            @PathVariable Long meetingId,
            @RequestBody MeetingParticipantDTO participantDTO) {
        return participantService.addParticipant(meetingId, participantDTO);
    }

    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateParticipantStatus(
            @PathVariable Long meetingId,
            @PathVariable Long userId,
            @RequestBody MeetingParticipantDTO participantDTO) {
        participantService.updateParticipantStatus(meetingId, userId, participantDTO);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParticipant(
            @PathVariable Long meetingId,
            @PathVariable Long userId) {
        participantService.removeParticipant(meetingId, userId);
    }
}