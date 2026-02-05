package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {
    @Autowired
    InvitationService invitationService;

    // TODO: When security is added
    @PatchMapping("/")
    @Operation(summary = "Answer Invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid Data"),
    })
    ResponseEntity<InvitationDTO> answerInvitation(@RequestBody @Valid InvitationAnswerDTO invitationAnswerDTO, Authentication authentication) {
        return ResponseEntity.ok(invitationService.answerInvitation(invitationAnswerDTO, authentication.getName()));
    }

    @PostMapping("/")
    @Operation(summary = "Create Invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid Data"),
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
}
