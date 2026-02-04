package ru.sicampus.bootcamp2026.dto

import java.time.LocalDateTime

data class UserResponseDto(
    val id: Long,
    val email: String,
    val createdAt: LocalDateTime
)

data class UserUpdateDto(
    val email: String? = null,
    val password: String? = null
)
