package ru.sicampus.bootcamp2026.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sicampus.bootcamp2026.dto.InvitationCreateDto
import ru.sicampus.bootcamp2026.dto.InvitationResponseDto
import ru.sicampus.bootcamp2026.dto.InvitationUpdateDto
import ru.sicampus.bootcamp2026.entity.InvitationStatus
import ru.sicampus.bootcamp2026.service.InvitationService

@RestController
@RequestMapping("/api/invitations")
class InvitationController(
    private val invitationService: InvitationService
) {
    @GetMapping
    fun getInvitations(
        @RequestParam(required = false) meetingId: Long?,
        @RequestParam(required = false) userId: Long?,
        @RequestParam(required = false) status: InvitationStatus?
    ): ResponseEntity<List<InvitationResponseDto>> {
        return ResponseEntity.ok(invitationService.getInvitations(meetingId, userId, status))
    }

    @GetMapping("/{id}")
    fun getInvitationById(@PathVariable id: Long): ResponseEntity<InvitationResponseDto> {
        return ResponseEntity.ok(invitationService.getInvitationById(id))
    }

    @PostMapping
    fun createInvitation(@RequestBody dto: InvitationCreateDto): ResponseEntity<InvitationResponseDto> {
        val createdInvitation = invitationService.createInvitation(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvitation)
    }

    @PutMapping("/{id}")
    fun updateInvitation(
        @PathVariable id: Long,
        @RequestBody dto: InvitationUpdateDto
    ): ResponseEntity<InvitationResponseDto> {
        return ResponseEntity.ok(invitationService.updateInvitation(id, dto))
    }

    @DeleteMapping("/{id}")
    fun deleteInvitation(@PathVariable id: Long): ResponseEntity<Void> {
        invitationService.deleteInvitation(id)
        return ResponseEntity.noContent().build()
    }
}
