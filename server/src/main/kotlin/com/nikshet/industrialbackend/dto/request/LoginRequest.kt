package com.nikshet.industrialbackend.dto.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Требуется указать номер телефона")
    val phoneNumber: String,

    @field:NotBlank(message = "Требуется указать пароль")
    val password: String
)