package com.nikshet.industrialbackend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "phone_number", nullable = false, unique = true)
    val phoneNumber: String,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @Column(nullable = false)
    var department: String,

    @Column(name = "password_hash", nullable = false)
    val passwordHash: String,
)