package ru.sicampus.bootcamp2026.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    @PostMapping("/")
    @Operation(summary = "Create meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),

    })
    ResponseEntity<MeetingDTO> createMeeting(@RequestBody @Valid MeetingCreateDTO meetingCreateDTO, Authentication authentication) {
        return ResponseEntity.ok(meetingService.createMeeting(meetingCreateDTO, authentication.getName()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a meeting by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Meeting not found")

    })
    ResponseEntity<MeetingDTO> getMeetingByID(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingByID(id));
    }

    @GetMapping("/{id}/participants")
    @Operation(summary = "Get meeting participants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Meeting not found")

    })
    ResponseEntity<List<InvitationEmployeeDTO>> getMeetingParticipants(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getEmployeesByMeetingID(id));
    }

    @GetMapping("/schedule")
    @Operation(summary = "Get meetings schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),

    })
    ResponseEntity<List<MeetingDTO>> getSchedule(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end, Authentication authentication) {
        return ResponseEntity.ok(meetingService.getSchedule(start, end, authentication.getName()));
    }
}
