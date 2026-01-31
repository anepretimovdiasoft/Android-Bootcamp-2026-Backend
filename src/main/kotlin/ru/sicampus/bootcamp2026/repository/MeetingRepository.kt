package ru.sicampus.bootcamp2026.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.sicampus.bootcamp2026.entity.Meeting
import java.time.LocalDate

@Repository
interface MeetingRepository : JpaRepository<Meeting, Long> {
    fun findByOrganizerId(organizerId: Long): List<Meeting>
    fun findByDate(date: LocalDate): List<Meeting>
}
