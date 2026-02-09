package com.nikshet.industrialbackend.service

import com.nikshet.industrialbackend.dto.request.UpdateProfileRequest
import com.nikshet.industrialbackend.entity.UserEntity
import com.nikshet.industrialbackend.exception.PhoneAlreadyUsedException
import com.nikshet.industrialbackend.repository.UsersRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID


@Service
class UsersService(
    private val usersRepository: UsersRepository,
    private val passwordService: PasswordService
) {
    fun registerUser(
        phoneNumber: String,
        fullName: String,
        department: String,
        password: String
    ): UserEntity {
        if (usersRepository.findByPhoneNumber(phoneNumber) != null) {
            throw PhoneAlreadyUsedException()
        }

        val passwordHash = passwordService.hashPassword(password)

        val user = UserEntity(
            phoneNumber = phoneNumber,
            fullName = fullName,
            department = department,
            passwordHash = passwordHash
        )

        return usersRepository.save(user)
    }

    fun authenticate(phoneNumber: String, rawPassword: String): UserEntity? {
        val user = usersRepository.findByPhoneNumber(phoneNumber)
            ?: return null

        val isPasswordValid = passwordService.verifyPassword(rawPassword, user.passwordHash)
        return if (isPasswordValid) user else null
    }

    fun updateUserProfile(userId: UUID, request: UpdateProfileRequest): UserEntity {
        val user = usersRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.fullName = request.fullName
        user.department = request.department

        return usersRepository.save(user)
    }

    fun getUserById(userId: UUID): UserEntity {
        val user = usersRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        return user
    }

    fun getAllUsers(): List<UserEntity> {
        return usersRepository.findAll()
    }
}