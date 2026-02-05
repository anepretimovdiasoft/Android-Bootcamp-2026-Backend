package ru.sicampus.bootcamp2026.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.sicampus.bootcamp2026.repository.UserRepository

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User with email $email not found") }

        return User.builder()
            .username(user.email)
            .password(user.password)
            .authorities("ROLE_${user.role.name}")
            .build()
    }
}
