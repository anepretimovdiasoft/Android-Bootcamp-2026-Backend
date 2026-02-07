package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {
    @Autowired
    InvitationService invitationService;

    // TODO: When security is added
    @PatchMapping("")
    @Operation(summary = "Answer Invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid Data"),
            @ApiResponse(responseCode = "404", description = "Invitation not found"),
            @ApiResponse(responseCode = "409", description = "Invitation not owned by user /  Employee is busy at this time")
    })
    ResponseEntity<InvitationDTO> answerInvitation(@RequestBody @Valid InvitationAnswerDTO invitationAnswerDTO, Authentication authentication) {
        return ResponseEntity.ok(invitationService.answerInvitation(invitationAnswerDTO, authentication.getName()));
    }

    @PostMapping("")
    @Operation(summary = "Create Invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid Data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Meeting not owned by user / Invitation already exists / Employee is busy at this time")
    })
    ResponseEntity<InvitationDTO> createInvitation(@RequestBody @Valid InvitationCreateDTO invitationCreateDTO, Authentication authentication) {
        return ResponseEntity.ok(invitationService.createInvitation(invitationCreateDTO, authentication.getName()));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active invitations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    ResponseEntity<List<InvitationMeetingDTO>> getActiveInvitations(Authentication authentication) {
        return ResponseEntity.ok(invitationService.getActiveInvitations(authentication.getName()));
    }

    @PostMapping("/batch")
    @Operation(summary = "Create Invitations for multiple users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid Data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Meeting not owned by user / Invitation already exists / Employee is busy at this time")
    })
    ResponseEntity<List<InvitationDTO>> createInvitationsBatch(@RequestBody @Valid InvitationCreateBatchDTO dto, Authentication authentication) {
        return ResponseEntity.ok(invitationService.createInvitationsBatch(dto, authentication.getName()));
    }
}
