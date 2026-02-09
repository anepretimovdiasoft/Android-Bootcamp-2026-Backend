package com.nikshet.industrialbackend.dto.response

import com.nikshet.industrialbackend.entity.enums.MeetingStatus
import com.nikshet.industrialbackend.entity.enums.InvitationStatus
import java.time.Instant
import java.util.*

data class MeetingResponse(
    val id: UUID,
    val organizerId: UUID,
    val title: String,
    val description: String?,
    val location: String,
    val startTime: Instant,
    val durationHours: Short,
    val status: MeetingStatus,
    val invitations: List<InvitationSummary>
)

data class InvitationSummary(
    val inviteeId: UUID,
    val status: InvitationStatus,
    val invitedAt: Instant,
    val respondedAt: Instant?
)