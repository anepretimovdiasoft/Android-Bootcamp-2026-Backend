package com.nikshet.industrialbackend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserRegistrationRequest(
    @field:NotBlank(message = "ФИО не может быть пустым")
    @field:Size(max = 100, message = "ФИО слишком длинное")
    val fullName: String,

    @field:NotBlank(message = "Неверный формат номера телефона. Ожидается +79123456789 или 89123456789")
    @field:Pattern(
        regexp = """^(\+7|8)\d{10}$""",
        message = "Неверный формат номера телефона. Ожидается +79123456789 или 89123456789"
    )
    val phoneNumber: String,

    @field:NotBlank(message = "Должен быть указан департамент")
    @field:Size(max = 100, message = "Название департамента слишком длинное")
    val department: String,

    @field:NotBlank(message = "Пароль не может быть пустым")
    @field:Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    val password: String
)