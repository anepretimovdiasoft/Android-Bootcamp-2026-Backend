package com.nikshet.industrialbackend.service


import de.mkammerer.argon2.Argon2Factory
import de.mkammerer.argon2.Argon2Factory.Argon2Types
import org.springframework.stereotype.Service

@Service
class PasswordService {
    private val argon2 = Argon2Factory.create(Argon2Types.ARGON2id)

    private val MEMORY_COST = 65536
    private val ITERATIONS = 3
    private val PARALLELISM = 1
    private val HASH_LENGTH = 32

    fun hashPassword(rawPassword: String): String {
        return argon2.hash(ITERATIONS, MEMORY_COST, PARALLELISM, rawPassword.toCharArray())
    }

    fun verifyPassword(rawPassword: String, hashedPassword: String): Boolean {
        return argon2.verify(hashedPassword, rawPassword.toCharArray())
    }
}