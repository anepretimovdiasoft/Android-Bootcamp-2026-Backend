package ru.sicampus.bootcamp2026.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sicampus.bootcamp2026.dto.UserResponseDto
import ru.sicampus.bootcamp2026.dto.UserUpdateDto
import ru.sicampus.bootcamp2026.entity.User
import ru.sicampus.bootcamp2026.repository.UserRepository
import ru.sicampus.bootcamp2026.security.SecurityUtils
import java.util.*

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun getAllUsers(): List<UserResponseDto> {
        return userRepository.findAll().map { it.toResponseDto() }
    }

    fun getUserById(id: Long): UserResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, id)
        
        return user.toResponseDto()
    }

    fun updateUser(id: Long, dto: UserUpdateDto): UserResponseDto {
        val currentUser = SecurityUtils.getCurrentUser(userRepository)
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }
        
        SecurityUtils.requireOwnershipOrAdmin(currentUser, id)
        
        dto.email?.let { user.email = it }
        dto.password?.let { user.password = passwordEncoder.encode(it).toString() }
        
        return userRepository.save(user).toResponseDto()
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    private fun User.toResponseDto() = UserResponseDto(
        id = this.id,
        email = this.email,
        createdAt = this.createdAt
    )
}
