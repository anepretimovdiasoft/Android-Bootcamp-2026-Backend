package ru.sicampus.bootcamp2026.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.sicampus.bootcamp2026.entity.Invitation
import ru.sicampus.bootcamp2026.entity.InvitationStatus

@Repository
interface InvitationRepository : JpaRepository<Invitation, Long> {
    fun findByMeetingId(meetingId: Long): List<Invitation>
    fun findByUserId(userId: Long): List<Invitation>
    fun findByMeetingIdAndUserId(meetingId: Long, userId: Long): List<Invitation>
    fun findByStatus(status: InvitationStatus): List<Invitation>
}
