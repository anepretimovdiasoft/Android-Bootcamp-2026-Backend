package com.nikshet.industrialbackend.service

import com.nikshet.industrialbackend.repository.UsersRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsService(
    private val usersRepository: UsersRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userId = UUID.fromString(username)
        val userEntity = usersRepository.findById(userId)
            .orElseThrow { UsernameNotFoundException("User not found") }

        return User(
            userEntity.phoneNumber,
            null,
            emptyList()
        )
    }
}