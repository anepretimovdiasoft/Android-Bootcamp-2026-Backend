package ru.sicampus.bootcamp2026.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "meetings")
class Meeting(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    val organizer: User,

    @Column(nullable = false)
    var title: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
