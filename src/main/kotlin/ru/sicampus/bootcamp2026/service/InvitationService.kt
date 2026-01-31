package ru.sicampus.bootcamp2026.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sicampus.bootcamp2026.dto.InvitationCreateDto
import ru.sicampus.bootcamp2026.dto.InvitationResponseDto
import ru.sicampus.bootcamp2026.dto.InvitationUpdateDto
import ru.sicampus.bootcamp2026.entity.Invitation
import ru.sicampus.bootcamp2026.entity.InvitationStatus
import ru.sicampus.bootcamp2026.repository.InvitationRepository
import ru.sicampus.bootcamp2026.repository.MeetingRepository
import ru.sicampus.bootcamp2026.repository.UserRepository
import java.util.*

@Service
@Transactional
class InvitationService(
    private val invitationRepository: InvitationRepository,
    private val meetingRepository: MeetingRepository,
    private val userRepository: UserRepository
) {
    fun getInvitations(
        meetingId: Long? = null,
        userId: Long? = null,
        status: InvitationStatus? = null
    ): List<InvitationResponseDto> {
        return when {
            meetingId != null && userId != null && status != null -> {
                invitationRepository.findByMeetingId(meetingId)
                    .filter { it.user.id == userId && it.status == status }
                    .map { it.toResponseDto() }
            }
            meetingId != null && userId != null -> {
                invitationRepository.findByMeetingIdAndUserId(meetingId, userId)
                    .map { it.toResponseDto() }
            }
            meetingId != null && status != null -> {
                invitationRepository.findByMeetingId(meetingId)
                    .filter { it.status == status }
                    .map { it.toResponseDto() }
            }
            userId != null && status != null -> {
                invitationRepository.findByUserId(userId)
                    .filter { it.status == status }
                    .map { it.toResponseDto() }
            }
            meetingId != null -> {
                invitationRepository.findByMeetingId(meetingId).map { it.toResponseDto() }
            }
            userId != null -> {
                invitationRepository.findByUserId(userId).map { it.toResponseDto() }
            }
            status != null -> {
                invitationRepository.findByStatus(status).map { it.toResponseDto() }
            }
            else -> {
                invitationRepository.findAll().map { it.toResponseDto() }
            }
        }
    }

    fun getInvitationById(id: Long): InvitationResponseDto {
        val invitation = invitationRepository.findById(id)
            .orElseThrow { NoSuchElementException("Invitation with id $id not found") }
        return invitation.toResponseDto()
    }

    fun createInvitation(dto: InvitationCreateDto): InvitationResponseDto {
        val meeting = meetingRepository.findById(dto.meetingId)
            .orElseThrow { NoSuchElementException("Meeting with id ${dto.meetingId} not found") }
        
        val user = userRepository.findById(dto.userId)
            .orElseThrow { NoSuchElementException("User with id ${dto.userId} not found") }
        
        // Проверка, что пользователь не является организатором встречи
        if (meeting.organizer.id == dto.userId) {
            throw IllegalArgumentException("Organizer cannot be invited to their own meeting")
        }
        
        // Проверка, что приглашение еще не существует
        val existingInvitations = invitationRepository.findByMeetingIdAndUserId(dto.meetingId, dto.userId)
        if (existingInvitations.isNotEmpty()) {
            throw IllegalArgumentException("Invitation for user ${dto.userId} to meeting ${dto.meetingId} already exists")
        }
        
        val invitation = Invitation(
            meeting = meeting,
            user = user,
            status = InvitationStatus.PENDING
        )
        return invitationRepository.save(invitation).toResponseDto()
    }

    fun updateInvitation(id: Long, dto: InvitationUpdateDto): InvitationResponseDto {
        val invitation = invitationRepository.findById(id)
            .orElseThrow { NoSuchElementException("Invitation with id $id not found") }
        
        invitation.status = dto.status
        
        return invitationRepository.save(invitation).toResponseDto()
    }

    fun deleteInvitation(id: Long) {
        if (!invitationRepository.existsById(id)) {
            throw NoSuchElementException("Invitation with id $id not found")
        }
        invitationRepository.deleteById(id)
    }

    private fun Invitation.toResponseDto() = InvitationResponseDto(
        id = this.id,
        meetingId = this.meeting.id,
        userId = this.user.id,
        status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
