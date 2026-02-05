package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sicampus.bootcamp2026.dto.RegisterDto;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.service.AuthService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto dto) {
        UserDto user = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }
}
