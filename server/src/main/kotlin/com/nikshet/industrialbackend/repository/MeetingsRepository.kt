package com.nikshet.industrialbackend.repository

import com.nikshet.industrialbackend.entity.MeetingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingsRepository : JpaRepository<MeetingEntity, UUID> {
    fun findByOrganizerId(organizerId: UUID): List<MeetingEntity>

    fun findByOrganizerIdAndStartTimeBetween(
        organizerId: UUID, startTime: java.time.Instant, startTime2: java.time.Instant
    ): List<MeetingEntity>
}