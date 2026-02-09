package com.nikshet.industrialbackend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    @field:NotBlank(message = "Full name is required")
    @field:Size(max = 100, message = "Full name is too long")
    val fullName: String,

    @field:NotBlank(message = "Department is required")
    @field:Size(max = 100, message = "Department name is too long")
    val department: String
)