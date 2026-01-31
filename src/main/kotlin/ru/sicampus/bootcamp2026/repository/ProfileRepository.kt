package ru.sicampus.bootcamp2026.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.sicampus.bootcamp2026.entity.Profile
import java.util.*

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByUserId(userId: Long): Optional<Profile>
}
