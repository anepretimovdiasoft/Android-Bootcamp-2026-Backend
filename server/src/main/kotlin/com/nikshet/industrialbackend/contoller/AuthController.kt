package com.nikshet.industrialbackend.contoller

import com.nikshet.industrialbackend.domain.TokensEntity
import com.nikshet.industrialbackend.dto.request.LoginRequest
import com.nikshet.industrialbackend.exception.InvalidLoginOrPasswordException
import com.nikshet.industrialbackend.providers.JwtTokensProvider
import com.nikshet.industrialbackend.service.UsersService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usersService: UsersService,
    private val tokensProvider: JwtTokensProvider
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<TokensEntity> {
        val user = usersService.authenticate(request.phoneNumber, request.password)
            ?: throw InvalidLoginOrPasswordException()

        val payload: Map<Any, Any> = mapOf(
            "sub" to user.id.toString(),
            "phone" to user.phoneNumber
        )

        val tokens = tokensProvider.generateTokensPairs(payload)
        return ResponseEntity.ok(tokens)
    }
}