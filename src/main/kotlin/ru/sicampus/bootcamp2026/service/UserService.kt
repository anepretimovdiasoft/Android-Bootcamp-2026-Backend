package ru.sicampus.bootcamp2026.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sicampus.bootcamp2026.dto.UserCreateDto
import ru.sicampus.bootcamp2026.dto.UserResponseDto
import ru.sicampus.bootcamp2026.dto.UserUpdateDto
import ru.sicampus.bootcamp2026.entity.User
import ru.sicampus.bootcamp2026.repository.UserRepository
import java.util.*

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<UserResponseDto> {
        return userRepository.findAll().map { it.toResponseDto() }
    }

    fun getUserById(id: Long): UserResponseDto {
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }
        return user.toResponseDto()
    }

    fun createUser(dto: UserCreateDto): UserResponseDto {
        if (userRepository.findByEmail(dto.email).isPresent) {
            throw IllegalArgumentException("User with email ${dto.email} already exists")
        }
        val user = User(
            email = dto.email,
            password = dto.password
        )
        return userRepository.save(user).toResponseDto()
    }

    fun updateUser(id: Long, dto: UserUpdateDto): UserResponseDto {
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }
        
        dto.email?.let { user.email = it }
        dto.password?.let { user.password = it }
        
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
