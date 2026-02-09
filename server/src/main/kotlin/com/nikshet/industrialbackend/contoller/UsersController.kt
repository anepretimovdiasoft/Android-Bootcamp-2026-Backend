package com.nikshet.industrialbackend.contoller

import com.nikshet.industrialbackend.domain.TokensEntity
import com.nikshet.industrialbackend.dto.request.UpdateProfileRequest
import com.nikshet.industrialbackend.dto.request.UserRegistrationRequest
import com.nikshet.industrialbackend.dto.response.UserInfoResponse
import com.nikshet.industrialbackend.providers.JwtTokensProvider
import com.nikshet.industrialbackend.service.UsersService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UsersController(
    private val usersService: UsersService,
    private val tokensProvider: JwtTokensProvider
) {
    @PostMapping("/registration")
    fun registerUser(@Valid @RequestBody request: UserRegistrationRequest): ResponseEntity<TokensEntity> {
        val user = usersService.registerUser(
            phoneNumber = request.phoneNumber,
            fullName = request.fullName,
            department = request.department,
            password = request.password
        )

        val payload: Map<Any, Any> = mapOf(
            "sub" to user.id.toString(),
            "phone" to user.phoneNumber
        )

        val tokens = tokensProvider.generateTokensPairs(payload)
        return ResponseEntity.status(HttpStatus.CREATED).body(tokens)
    }

    @PutMapping("/profile")
    fun updateProfile(
        @AuthenticationPrincipal userId: UUID,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<UserInfoResponse> {
        val updatedUser = usersService.updateUserProfile(userId, request)
        return ResponseEntity.ok(UserInfoResponse.from(updatedUser))
    }

    @GetMapping("/me")
    fun getProfile(@AuthenticationPrincipal userId: UUID): ResponseEntity<UserInfoResponse> {
        val user = usersService.getUserById(userId)

        return ResponseEntity.ok(UserInfoResponse.from(user))
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserInfoResponse>> {
        val users = usersService.getAllUsers()

        return ResponseEntity.ok(users.map { UserInfoResponse.from(it) })
    }
}