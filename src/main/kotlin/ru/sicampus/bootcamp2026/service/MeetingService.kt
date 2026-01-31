package ru.sicampus.bootcamp2026.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sicampus.bootcamp2026.dto.MeetingCreateDto
import ru.sicampus.bootcamp2026.dto.MeetingResponseDto
import ru.sicampus.bootcamp2026.dto.MeetingUpdateDto
import ru.sicampus.bootcamp2026.entity.Meeting
import ru.sicampus.bootcamp2026.repository.MeetingRepository
import ru.sicampus.bootcamp2026.repository.UserRepository
import java.time.LocalDate
import java.util.*

@Service
@Transactional
class MeetingService(
    private val meetingRepository: MeetingRepository,
    private val userRepository: UserRepository
) {
    fun getMeetings(
        organizerId: Long? = null,
        date: LocalDate? = null
    ): List<MeetingResponseDto> {
        return when {
            organizerId != null && date != null -> {
                meetingRepository.findByOrganizerId(organizerId)
                    .filter { it.date == date }
                    .map { it.toResponseDto() }
            }
            organizerId != null -> {
                meetingRepository.findByOrganizerId(organizerId).map { it.toResponseDto() }
            }
            date != null -> {
                meetingRepository.findByDate(date).map { it.toResponseDto() }
            }
            else -> {
                meetingRepository.findAll().map { it.toResponseDto() }
            }
        }
    }

    fun getMeetingById(id: Long): MeetingResponseDto {
        val meeting = meetingRepository.findById(id)
            .orElseThrow { NoSuchElementException("Meeting with id $id not found") }
        return meeting.toResponseDto()
    }

    fun createMeeting(dto: MeetingCreateDto): MeetingResponseDto {
        val organizer = userRepository.findById(dto.organizerId)
            .orElseThrow { NoSuchElementException("User with id ${dto.organizerId} not found") }
        
        if (dto.endTime.isBefore(dto.startTime)) {
            throw IllegalArgumentException("End time must be after start time")
        }
        
        val meeting = Meeting(
            organizer = organizer,
            title = dto.title,
            description = dto.description,
            date = dto.date,
            startTime = dto.startTime,
            endTime = dto.endTime
        )
        return meetingRepository.save(meeting).toResponseDto()
    }

    fun updateMeeting(id: Long, dto: MeetingUpdateDto): MeetingResponseDto {
        val meeting = meetingRepository.findById(id)
            .orElseThrow { NoSuchElementException("Meeting with id $id not found") }
        
        dto.title?.let { meeting.title = it }
        dto.description?.let { meeting.description = it }
        dto.date?.let { meeting.date = it }
        dto.startTime?.let { meeting.startTime = it }
        dto.endTime?.let { meeting.endTime = it }
        
        if (meeting.endTime.isBefore(meeting.startTime)) {
            throw IllegalArgumentException("End time must be after start time")
        }
        
        return meetingRepository.save(meeting).toResponseDto()
    }

    fun deleteMeeting(id: Long) {
        if (!meetingRepository.existsById(id)) {
            throw NoSuchElementException("Meeting with id $id not found")
        }
        meetingRepository.deleteById(id)
    }

    private fun Meeting.toResponseDto() = MeetingResponseDto(
        id = this.id,
        organizerId = this.organizer.id,
        title = this.title,
        description = this.description,
        date = this.date,
        startTime = this.startTime,
        endTime = this.endTime,
        createdAt = this.createdAt
    )
}
