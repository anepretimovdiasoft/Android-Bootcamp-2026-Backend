package ru.sicampus.bootcamp2026.service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sicampus.bootcamp2026.dto.AuthResponseDto
import ru.sicampus.bootcamp2026.dto.RegisterDto
import ru.sicampus.bootcamp2026.entity.User
import ru.sicampus.bootcamp2026.entity.UserRole
import ru.sicampus.bootcamp2026.repository.UserRepository

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(dto: RegisterDto): AuthResponseDto {
        if (userRepository.findByEmail(dto.email).isPresent) {
            throw IllegalArgumentException("User with email ${dto.email} already exists")
        }

        val hashedPassword = passwordEncoder.encode(dto.password)
        if (hashedPassword.isNullOrEmpty()) {
            throw IllegalArgumentException("Failed to hash password")
        }
        
        val user = User(
            email = dto.email,
            password = hashedPassword,
            role = UserRole.USER
        )

        val savedUser = userRepository.save(user)
        return savedUser.toAuthResponseDto()
    }

    fun getCurrentUser(): AuthResponseDto {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication !is UsernamePasswordAuthenticationToken) {
            throw IllegalStateException("Authentication is not a UsernamePasswordAuthenticationToken")
        }

        val email = authentication.name
        val user = userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("Current user not found") }

        return user.toAuthResponseDto()
    }

    private fun User.toAuthResponseDto() = AuthResponseDto(
        id = this.id,
        email = this.email,
        role = this.role.name,
        createdAt = this.createdAt
    )
}
