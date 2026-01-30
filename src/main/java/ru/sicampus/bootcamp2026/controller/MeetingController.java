package ru.sicampus.bootcamp2026.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    // TODO: When security is added
    @PostMapping("/")
    @Operation(summary = "Create meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),

    })
    ResponseEntity<MeetingDTO> createMeeting(@RequestBody MeetingCreateDTO meetingCreateDTO) {
        return ResponseEntity.ok(meetingService.createMeeting(meetingCreateDTO));
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

}
