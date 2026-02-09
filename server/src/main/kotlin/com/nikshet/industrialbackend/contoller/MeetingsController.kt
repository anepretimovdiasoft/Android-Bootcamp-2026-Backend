package com.nikshet.industrialbackend.contoller

import com.nikshet.industrialbackend.dto.request.CreateMeetingRequest
import com.nikshet.industrialbackend.dto.response.InvitationResponse
import com.nikshet.industrialbackend.dto.response.MeetingResponse
import com.nikshet.industrialbackend.service.MeetingsService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*


@RestController
@RequestMapping("/meetings")
class MeetingsController(
    private val meetingsService: MeetingsService
) {

    @PostMapping
    fun createMeeting(
        @AuthenticationPrincipal userId: UUID,
        @Valid @RequestBody request: CreateMeetingRequest
    ): ResponseEntity<MeetingResponse> {
        val meeting = meetingsService.createMeeting(userId, request)
        return ResponseEntity.ok(meeting)
    }

    @GetMapping("/{meetingId}")
    fun getMeeting(
        @AuthenticationPrincipal userId: UUID,
        @PathVariable meetingId: UUID
    ): ResponseEntity<MeetingResponse> {
        val meeting = meetingsService.getMeetingById(meetingId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(meeting)
    }

    @GetMapping
    fun getMyMeetings(
        @AuthenticationPrincipal userId: UUID
    ): ResponseEntity<List<MeetingResponse>> {
        val meetings = meetingsService.getMeetingsForUser(userId)
        return ResponseEntity.ok(meetings)
    }

    @GetMapping("/invitations")
    fun getMyInvitations(
        @AuthenticationPrincipal userId: UUID
    ): ResponseEntity<List<InvitationResponse>> {
        val invitations = meetingsService.getInvitationsForUser(userId)
        return ResponseEntity.ok(invitations)
    }

    @PostMapping("/invitations/{invitationId}/accept")
    fun acceptInvitation(
        @AuthenticationPrincipal userId: UUID,
        @PathVariable invitationId: UUID
    ): ResponseEntity<Unit> {
        meetingsService.respondToInvitation(invitationId, userId, accept = true)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/invitations/{invitationId}/decline")
    fun declineInvitation(
        @AuthenticationPrincipal userId: UUID,
        @PathVariable invitationId: UUID
    ): ResponseEntity<Unit> {
        meetingsService.respondToInvitation(invitationId, userId, accept = false)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/schedule")
    fun getSchedule(
        @AuthenticationPrincipal userId: UUID,
        @RequestParam(required = false) period: String? = null,
        @RequestParam(required = false) date: String? = null // ISO_LOCAL_DATE, e.g., "2026-02-07"
    ): ResponseEntity<List<MeetingResponse>> {
        val localDate = date?.let { LocalDate.parse(it) } ?: LocalDate.now()

        val meetings = when (period?.uppercase()) {
            "DAY" -> meetingsService.getMeetingsForUserInPeriod(userId, localDate, localDate)
            "WEEK" -> {
                val weekFields = WeekFields.of(Locale.getDefault())
                val firstDay = localDate.with(weekFields.dayOfWeek(), 1)
                val lastDay = localDate.with(weekFields.dayOfWeek(), 7)
                meetingsService.getMeetingsForUserInPeriod(userId, firstDay, lastDay)
            }

            "MONTH" -> {
                val firstDay = localDate.withDayOfMonth(1)
                val lastDay = localDate.withDayOfMonth(localDate.lengthOfMonth())
                meetingsService.getMeetingsForUserInPeriod(userId, firstDay, lastDay)
            }

            else -> meetingsService.getMeetingsForUser(userId)
        }

        return ResponseEntity.ok(meetings)
    }
}
