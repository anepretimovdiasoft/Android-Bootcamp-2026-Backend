package ru.sicampus.bootcamp2026.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sicampus.bootcamp2026.dto.ProfileCreateDto
import ru.sicampus.bootcamp2026.dto.ProfileResponseDto
import ru.sicampus.bootcamp2026.dto.ProfileUpdateDto
import ru.sicampus.bootcamp2026.service.ProfileService

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping
    fun getAllProfiles(): ResponseEntity<List<ProfileResponseDto>> {
        return ResponseEntity.ok(profileService.getAllProfiles())
    }

    @GetMapping("/{id}")
    fun getProfileById(@PathVariable id: Long): ResponseEntity<ProfileResponseDto> {
        return ResponseEntity.ok(profileService.getProfileById(id))
    }

    @GetMapping("/user/{userId}")
    fun getProfileByUserId(@PathVariable userId: Long): ResponseEntity<ProfileResponseDto> {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId))
    }

    @PostMapping
    fun createProfile(@RequestBody dto: ProfileCreateDto): ResponseEntity<ProfileResponseDto> {
        val createdProfile = profileService.createProfile(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile)
    }

    @PutMapping("/{id}")
    fun updateProfile(
        @PathVariable id: Long,
        @RequestBody dto: ProfileUpdateDto
    ): ResponseEntity<ProfileResponseDto> {
        return ResponseEntity.ok(profileService.updateProfile(id, dto))
    }

    @DeleteMapping("/{id}")
    fun deleteProfile(@PathVariable id: Long): ResponseEntity<Void> {
        profileService.deleteProfile(id)
        return ResponseEntity.noContent().build()
    }
}
