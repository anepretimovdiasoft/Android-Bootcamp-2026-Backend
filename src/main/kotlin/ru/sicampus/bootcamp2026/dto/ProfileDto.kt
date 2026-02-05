package ru.sicampus.bootcamp2026.dto

import java.time.LocalDateTime

data class ProfileResponseDto(
    val id: Long,
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val photoUrl: String?,
    val phone: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class ProfileCreateDto(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val photoUrl: String? = null,
    val phone: String? = null
)

data class ProfileUpdateDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val photoUrl: String? = null,
    val phone: String? = null
)
