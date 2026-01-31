package ru.sicampus.bootcamp2026.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sicampus.bootcamp2026.dto.UserCreateDto
import ru.sicampus.bootcamp2026.dto.UserResponseDto
import ru.sicampus.bootcamp2026.dto.UserUpdateDto
import ru.sicampus.bootcamp2026.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponseDto>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PostMapping
    fun createUser(@RequestBody dto: UserCreateDto): ResponseEntity<UserResponseDto> {
        val createdUser = userService.createUser(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody dto: UserUpdateDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.updateUser(id, dto))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
