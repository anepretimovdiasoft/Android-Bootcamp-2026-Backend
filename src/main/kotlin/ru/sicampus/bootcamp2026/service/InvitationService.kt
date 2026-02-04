package ru.sicampus.bootcamp2026.service

import org.springframework.security.access.AccessDeniedException
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
import ru.sicampus.bootcamp2026.security.SecurityUtils
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
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val isAdmin = SecurityUtils.isAdmin(currentUser)
        
        userId?.let { 
            SecurityUtils.requireOwnershipOrAdmin(currentUser, it)
        }
        
        meetingId?.let {
            val meeting = meetingRepository.findById(it)
                .orElseThrow { NoSuchElementException("Meeting with id $it not found") }
            SecurityUtils.requireOwnershipOrAdmin(currentUser, meeting.organizer.id)
        }
        
        val effectiveUserId = if (isAdmin) userId else (userId ?: currentUser.id)
        
        val invitations = when {
            meetingId != null && effectiveUserId != null && status != null -> {
                invitationRepository.findByMeetingId(meetingId)
                    .filter { it.user.id == effectiveUserId && it.status == status }
            }
            meetingId != null && effectiveUserId != null -> {
                invitationRepository.findByMeetingIdAndUserId(meetingId, effectiveUserId)
            }
            meetingId != null && status != null -> {
                invitationRepository.findByMeetingId(meetingId)
                    .filter { it.status == status }
            }
            effectiveUserId != null && status != null -> {
                invitationRepository.findByUserId(effectiveUserId)
                    .filter { it.status == status }
            }
            meetingId != null -> {
                invitationRepository.findByMeetingId(meetingId)
            }
            effectiveUserId != null -> {
                invitationRepository.findByUserId(effectiveUserId)
            }
            status != null -> {
                if (isAdmin) {
                    invitationRepository.findByStatus(status)
                } else {
                    invitationRepository.findByUserId(currentUser.id)
                        .filter { it.status == status }
                }
            }
            else -> {
                if (isAdmin) {
                    invitationRepository.findAll()
                } else {
                    invitationRepository.findByUserId(currentUser.id)
                }
            }
        }
        
        return invitations.map { it.toResponseDto() }
    }

    fun getInvitationById(id: Long): InvitationResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val invitation = invitationRepository.findById(id)
            .orElseThrow { NoSuchElementException("Invitation with id $id not found") }
        
        if (
            currentUser.role.name != "ADMIN" 
            && invitation.user.id != currentUser.id 
            && invitation.meeting.organizer.id != currentUser.id
        ) {
            throw AccessDeniedException("Access denied")
        }
        
        return invitation.toResponseDto()
    }

    fun createInvitation(dto: InvitationCreateDto): InvitationResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val meeting = meetingRepository.findById(dto.meetingId)
            .orElseThrow { NoSuchElementException("Meeting with id ${dto.meetingId} not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, meeting.organizer.id)
        
        val user = userRepository.findById(dto.userId)
            .orElseThrow { NoSuchElementException("User with id ${dto.userId} not found") }
        
        if (meeting.organizer.id == dto.userId) {
            throw IllegalArgumentException("Organizer cannot be invited to their own meeting")
        }
        
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
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val invitation = invitationRepository.findById(id)
            .orElseThrow { NoSuchElementException("Invitation with id $id not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, invitation.user.id)
        
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
