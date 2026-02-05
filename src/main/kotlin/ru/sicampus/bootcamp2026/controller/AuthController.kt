package ru.sicampus.bootcamp2026.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sicampus.bootcamp2026.dto.AuthResponseDto
import ru.sicampus.bootcamp2026.dto.RegisterDto
import ru.sicampus.bootcamp2026.service.AuthService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody dto: RegisterDto): ResponseEntity<AuthResponseDto> {
        val registeredUser = authService.register(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser)
    }

    @GetMapping("/me")
    fun getCurrentUser(): ResponseEntity<AuthResponseDto> {
        return ResponseEntity.ok(authService.getCurrentUser())
    }
}
