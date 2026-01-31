package ru.sicampus.bootcamp2026.controller

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sicampus.bootcamp2026.dto.MeetingCreateDto
import ru.sicampus.bootcamp2026.dto.MeetingResponseDto
import ru.sicampus.bootcamp2026.dto.MeetingUpdateDto
import ru.sicampus.bootcamp2026.service.MeetingService
import java.time.LocalDate

@RestController
@RequestMapping("/api/meetings")
class MeetingController(
    private val meetingService: MeetingService
) {
    @GetMapping
    fun getMeetings(
        @RequestParam(required = false) organizerId: Long?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?
    ): ResponseEntity<List<MeetingResponseDto>> {
        return ResponseEntity.ok(meetingService.getMeetings(organizerId, date))
    }

    @GetMapping("/{id}")
    fun getMeetingById(@PathVariable id: Long): ResponseEntity<MeetingResponseDto> {
        return ResponseEntity.ok(meetingService.getMeetingById(id))
    }

    @PostMapping
    fun createMeeting(@RequestBody dto: MeetingCreateDto): ResponseEntity<MeetingResponseDto> {
        val createdMeeting = meetingService.createMeeting(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMeeting)
    }

    @PutMapping("/{id}")
    fun updateMeeting(
        @PathVariable id: Long,
        @RequestBody dto: MeetingUpdateDto
    ): ResponseEntity<MeetingResponseDto> {
        return ResponseEntity.ok(meetingService.updateMeeting(id, dto))
    }

    @DeleteMapping("/{id}")
    fun deleteMeeting(@PathVariable id: Long): ResponseEntity<Void> {
        meetingService.deleteMeeting(id)
        return ResponseEntity.noContent().build()
    }
}
