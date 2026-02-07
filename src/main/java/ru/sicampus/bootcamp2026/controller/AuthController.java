package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.service.AuthService;
import ru.sicampus.bootcamp2026.web.dto.user.UserDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserRegisterDto;
import ru.sicampus.bootcamp2026.web.mappers.UserMapper;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Аутентификация")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public UserDto login(Authentication authentication) {
        return UserMapper.toDto(authService.login(authentication));
    }

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody UserRegisterDto userRegisterDto) {
        return UserMapper.toDto(authService.register(userRegisterDto));
    }

}
