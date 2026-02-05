package ru.sicampus.bootcamp2026.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sicampus.bootcamp2026.dto.ProfileCreateDto
import ru.sicampus.bootcamp2026.dto.ProfileResponseDto
import ru.sicampus.bootcamp2026.dto.ProfileUpdateDto
import ru.sicampus.bootcamp2026.entity.Profile
import ru.sicampus.bootcamp2026.repository.ProfileRepository
import ru.sicampus.bootcamp2026.repository.UserRepository
import ru.sicampus.bootcamp2026.security.SecurityUtils
import java.util.*

@Service
@Transactional
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository
) {
    fun getAllProfiles(): List<ProfileResponseDto> {
        return profileRepository.findAll().map { it.toResponseDto() }
    }

    fun getProfileById(id: Long): ProfileResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val profile = profileRepository.findById(id)
            .orElseThrow { NoSuchElementException("Profile with id $id not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, profile.user.id)
        
        return profile.toResponseDto()
    }

    fun getProfileByUserId(userId: Long): ProfileResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val profile = profileRepository.findByUserId(userId)
            .orElseThrow { NoSuchElementException("Profile for user with id $userId not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, userId)
        
        return profile.toResponseDto()
    }

    fun createProfile(dto: ProfileCreateDto): ProfileResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        SecurityUtils.requireOwnershipOrAdmin(currentUser, dto.userId)
        
        val user = userRepository.findById(dto.userId)
            .orElseThrow { NoSuchElementException("User with id ${dto.userId} not found") }
        
        if (profileRepository.findByUserId(dto.userId).isPresent) {
            throw IllegalArgumentException("Profile for user with id ${dto.userId} already exists")
        }
        
        val profile = Profile(
            user = user,
            firstName = dto.firstName,
            lastName = dto.lastName,
            photoUrl = dto.photoUrl,
            phone = dto.phone
        )
        return profileRepository.save(profile).toResponseDto()
    }

    fun updateProfile(id: Long, dto: ProfileUpdateDto): ProfileResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val profile = profileRepository.findById(id)
            .orElseThrow { NoSuchElementException("Profile with id $id not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, profile.user.id)
        
        dto.firstName?.let { profile.firstName = it }
        dto.lastName?.let { profile.lastName = it }
        dto.photoUrl?.let { profile.photoUrl = it }
        dto.phone?.let { profile.phone = it }
        
        return profileRepository.save(profile).toResponseDto()
    }

    fun deleteProfile(id: Long) {
        if (!profileRepository.existsById(id)) {
            throw NoSuchElementException("Profile with id $id not found")
        }
        profileRepository.deleteById(id)
    }

    private fun Profile.toResponseDto() = ProfileResponseDto(
        id = this.id,
        userId = this.user.id,
        firstName = this.firstName,
        lastName = this.lastName,
        photoUrl = this.photoUrl,
        phone = this.phone,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
