package ru.sicampus.bootcamp2026.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class MeetingResponseDto(
    val id: Long,
    val organizerId: Long,
    val title: String,
    val description: String?,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val createdAt: LocalDateTime
)

data class MeetingCreateDto(
    val organizerId: Long,
    val title: String,
    val description: String? = null,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)

data class MeetingUpdateDto(
    val title: String? = null,
    val description: String? = null,
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null
)
