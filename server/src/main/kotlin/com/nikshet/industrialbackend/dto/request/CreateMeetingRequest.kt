package com.nikshet.industrialbackend.dto.request

import jakarta.validation.constraints.*
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

data class CreateMeetingRequest(
    @field:NotBlank
    @field:Size(max = 200)
    val title: String,

    @field:Size(max = 1000)
    val description: String? = null,

    @field:NotBlank
    @field:Size(max = 200)
    val location: String,

    @field:NotNull
    var startTime: Instant,

    @field:Min(1)
    @field:Max(8)
    val durationHours: Short,

    @field:NotEmpty
    val invitees: List<UUID>
) {
    init {
        val utcTime = startTime.atOffset(ZoneOffset.UTC).toLocalDateTime()
        require(utcTime.minute == 0 && utcTime.second == 0 && utcTime.nano == 0) {
            "Start time must be on the hour (e.g., 09:00, not 09:15). Got: $startTime"
        }
    }
}