package com.nikshet.industrialbackend.repository

import com.nikshet.industrialbackend.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsersRepository : JpaRepository<UserEntity, UUID> {
    fun findByPhoneNumber(phoneNumber: String): UserEntity?
}