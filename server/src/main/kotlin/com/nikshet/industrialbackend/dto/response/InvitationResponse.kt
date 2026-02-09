package com.nikshet.industrialbackend.dto.response

import com.nikshet.industrialbackend.entity.enums.InvitationStatus
import java.time.Instant
import java.util.*

data class InvitationResponse(
    val id: UUID,
    val meetingId: UUID,
    val meetingTitle: String,
    val meetingStartTime: Instant,
    val meetingLocation: String,
    val status: InvitationStatus,
    val invitedAt: Instant,
    val respondedAt: Instant?
)