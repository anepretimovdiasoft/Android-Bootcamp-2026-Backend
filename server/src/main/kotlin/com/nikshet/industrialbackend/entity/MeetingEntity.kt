package com.nikshet.industrialbackend.entity

import com.nikshet.industrialbackend.entity.enums.MeetingStatus
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "meetings")
data class MeetingEntity(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "organizer_id", nullable = false)
    val organizerId: UUID,

    @Column(nullable = false)
    val title: String,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(nullable = false)
    val location: String,

    @Column(name = "start_time", nullable = false)
    val startTime: Instant,

    @Column(name = "duration_hours", nullable = false)
    val durationHours: Short,

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    val status: MeetingStatus = MeetingStatus.SCHEDULED
)