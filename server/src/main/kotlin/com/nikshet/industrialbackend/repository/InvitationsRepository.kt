package com.nikshet.industrialbackend.repository

import com.nikshet.industrialbackend.entity.InvitationEntity
import com.nikshet.industrialbackend.entity.enums.InvitationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface InvitationsRepository : JpaRepository<InvitationEntity, UUID> {
    fun findByInviteeId(inviteeId: UUID): List<InvitationEntity>
    fun findByMeetingId(meetingId: UUID): List<InvitationEntity>

    @Query(
        """
        SELECT DISTINCT m.id 
        FROM InvitationEntity i 
        JOIN MeetingEntity m ON i.meetingId = m.id 
        WHERE i.inviteeId = :userId 
          AND m.startTime >= :start 
          AND m.startTime < :end
        """
    )
    fun findDistinctMeetingIdByInviteeIdAndMeetingStartTimeBetween(
        @Param("userId") userId: UUID,
        @Param("start") start: Instant,
        @Param("end") end: Instant
    ): List<UUID>
}