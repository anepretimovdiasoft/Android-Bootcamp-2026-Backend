package ru.sicampus.bootcamp2026.dto

import java.time.LocalDateTime

data class RegisterDto(
    val email: String,
    val password: String
)

data class AuthResponseDto(
    val id: Long,
    val email: String,
    val role: String,
    val createdAt: LocalDateTime
)
