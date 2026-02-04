package ru.sicampus.bootcamp2026.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import ru.sicampus.bootcamp2026.entity.User
import ru.sicampus.bootcamp2026.entity.UserRole
import ru.sicampus.bootcamp2026.repository.UserRepository

object SecurityUtils {
    fun getCurrentUserEmail(): String {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        return authentication?.name
            ?: throw IllegalStateException("No authenticated user found")
    }

    fun getCurrentUser(userRepository: UserRepository): User {
        val email = getCurrentUserEmail()
        return userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("Current user not found") }
    }

    fun isAdmin(user: User): Boolean = user.role == UserRole.ADMIN

    fun requireOwnershipOrAdmin(user: User, resourceOwnerId: Long) {
        if (!isAdmin(user) && user.id != resourceOwnerId) {
            throw AccessDeniedException("Access denied")
        }
    }

    fun requireAdmin(user: User) {
        if (!isAdmin(user)) {
            throw AccessDeniedException("Access denied")
        }
    }
}
