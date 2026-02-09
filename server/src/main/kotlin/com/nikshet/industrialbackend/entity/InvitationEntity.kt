package com.nikshet.industrialbackend.entity

import com.nikshet.industrialbackend.entity.enums.InvitationStatus
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(
    name = "invitations",
    uniqueConstraints = [UniqueConstraint(columnNames = ["meeting_id", "invitee_id"])]
)
data class InvitationEntity(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "meeting_id", nullable = false)
    val meetingId: UUID,

    @Column(name = "invitee_id", nullable = false)
    val inviteeId: UUID,

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var status: InvitationStatus = InvitationStatus.PENDING,

    @Column(name = "invited_at", nullable = false)
    val invitedAt: Instant = Instant.now(),

    @Column(name = "responded_at")
    var respondedAt: Instant? = null
)