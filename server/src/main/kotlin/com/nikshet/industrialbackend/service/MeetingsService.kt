package com.nikshet.industrialbackend.service

import com.nikshet.industrialbackend.dto.request.CreateMeetingRequest
import com.nikshet.industrialbackend.dto.response.InvitationResponse
import com.nikshet.industrialbackend.dto.response.MeetingResponse
import com.nikshet.industrialbackend.dto.response.InvitationSummary
import com.nikshet.industrialbackend.entity.MeetingEntity
import com.nikshet.industrialbackend.entity.InvitationEntity
import com.nikshet.industrialbackend.entity.enums.InvitationStatus
import com.nikshet.industrialbackend.repository.MeetingsRepository
import com.nikshet.industrialbackend.repository.InvitationsRepository
import com.nikshet.industrialbackend.repository.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@Service
class MeetingsService(
    private val meetingsRepository: MeetingsRepository,
    private val invitationsRepository: InvitationsRepository,
    private val usersRepository: UsersRepository
) {

    @Transactional
    fun createMeeting(organizerId: UUID, request: CreateMeetingRequest): MeetingResponse {
        request.invitees.forEach { userId ->
            if (!usersRepository.existsById(userId)) {
                throw IllegalArgumentException("User with ID $userId does not exist")
            }
        }

        val meeting = MeetingEntity(
            organizerId = organizerId,
            title = request.title,
            description = request.description,
            location = request.location,
            startTime = request.startTime,
            durationHours = request.durationHours
        )
        val savedMeeting = meetingsRepository.save(meeting)

        val invitations = request.invitees.map { inviteeId ->
            InvitationEntity(
                meetingId = savedMeeting.id,
                inviteeId = inviteeId
            )
        }
        invitationsRepository.saveAll(invitations)

        return toMeetingResponse(savedMeeting, invitations)
    }

    fun getMeetingById(meetingId: UUID): MeetingResponse? {
        val meeting = meetingsRepository.findById(meetingId).orElse(null) ?: return null
        val invitations = invitationsRepository.findByMeetingId(meetingId)
        return toMeetingResponse(meeting, invitations)
    }

    fun getMeetingsForUser(userId: UUID): List<MeetingResponse> {
        val organized = meetingsRepository.findByOrganizerId(userId)
        val invitedMeetingIds = invitationsRepository.findByInviteeId(userId).map { it.meetingId }.toSet()
        val invited = if (invitedMeetingIds.isNotEmpty()) {
            meetingsRepository.findAllById(invitedMeetingIds)
        } else {
            emptyList()
        }
        val allMeetings = (organized + invited).distinctBy { it.id }

        return allMeetings.map { meeting ->
            val invitations = invitationsRepository.findByMeetingId(meeting.id)
            toMeetingResponse(meeting, invitations)
        }
    }

    fun getInvitationsForUser(inviteeId: UUID): List<InvitationResponse> {
        val invitations = invitationsRepository.findByInviteeId(inviteeId)
        val meetingIds = invitations.map { it.meetingId }
        val meetings = if (meetingIds.isNotEmpty()) {
            meetingsRepository.findAllById(meetingIds).associateBy { it.id }
        } else {
            emptyMap()
        }

        return invitations.map { inv ->
            val meeting = meetings[inv.meetingId]!!
            InvitationResponse(
                id = inv.id,
                meetingId = inv.meetingId,
                meetingTitle = meeting.title,
                meetingStartTime = meeting.startTime,
                meetingLocation = meeting.location,
                status = inv.status,
                invitedAt = inv.invitedAt,
                respondedAt = inv.respondedAt
            )
        }
    }

    @Transactional
    fun respondToInvitation(invitationId: UUID, inviteeId: UUID, accept: Boolean) {
        val invitation = invitationsRepository.findById(invitationId)
            .orElseThrow { IllegalArgumentException("Invitation not found") }

        if (invitation.inviteeId != inviteeId) {
            throw IllegalArgumentException("You can only respond to your own invitations")
        }

        if (invitation.status != InvitationStatus.PENDING) {
            throw IllegalArgumentException("Invitation already responded to")
        }

        invitation.status = if (accept) InvitationStatus.ACCEPTED else InvitationStatus.DECLINED
        invitation.respondedAt = java.time.Instant.now()
        invitationsRepository.save(invitation)
    }

    private fun toMeetingResponse(meeting: MeetingEntity, invitations: List<InvitationEntity>): MeetingResponse {
        val summaries = invitations.map { inv ->
            InvitationSummary(
                inviteeId = inv.inviteeId,
                status = inv.status,
                invitedAt = inv.invitedAt,
                respondedAt = inv.respondedAt
            )
        }
        return MeetingResponse(
            id = meeting.id,
            organizerId = meeting.organizerId,
            title = meeting.title,
            description = meeting.description,
            location = meeting.location,
            startTime = meeting.startTime,
            durationHours = meeting.durationHours,
            status = meeting.status,
            invitations = summaries
        )
    }

    fun getMeetingsForUserInPeriod(userId: UUID, startDate: LocalDate, endDate: LocalDate): List<MeetingResponse> {
        val startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC)
        val endInstant = endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC)

        val organized = meetingsRepository.findByOrganizerIdAndStartTimeBetween(userId, startInstant, endInstant)
        val invitedMeetingIds = invitationsRepository
            .findDistinctMeetingIdByInviteeIdAndMeetingStartTimeBetween(userId, startInstant, endInstant)

        val invited = if (invitedMeetingIds.isNotEmpty()) {
            meetingsRepository.findAllById(invitedMeetingIds)
        } else {
            emptyList()
        }

        val allMeetings = (organized + invited).distinctBy { it.id }

        return allMeetings.map { meeting ->
            val invitations = invitationsRepository.findByMeetingId(meeting.id)
            toMeetingResponse(meeting, invitations)
        }
    }


}