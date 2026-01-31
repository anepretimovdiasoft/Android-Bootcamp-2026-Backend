package ru.sicampus.bootcamp2026.dto

import ru.sicampus.bootcamp2026.entity.InvitationStatus
import java.time.LocalDateTime

data class InvitationResponseDto(
    val id: Long,
    val meetingId: Long,
    val userId: Long,
    val status: InvitationStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class InvitationCreateDto(
    val meetingId: Long,
    val userId: Long
)

data class InvitationUpdateDto(
    val status: InvitationStatus
)
