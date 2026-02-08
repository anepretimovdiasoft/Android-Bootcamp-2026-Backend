package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.ErrorResponse;
import ru.sicampus.bootcamp2026.dto.request.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.exception.MeetingException;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping
    @Operation(summary = "Create meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Time conflict",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MeetingResponseDTO> createMeeting(@Valid @RequestBody MeetingCreateDTO dto) throws MeetingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(meetingService.createMeeting(dto));
    }

    @GetMapping("/schedule")
    @Operation(summary = "Get meeting schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingResponseDTO.class)))
    })
    public ResponseEntity<List<MeetingResponseDTO>> getSchedule() {
        return ResponseEntity.ok(meetingService.getSchedule());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "401",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<Void> deleteMeeting(@PathVariable long id) throws MeetingException {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }
}
