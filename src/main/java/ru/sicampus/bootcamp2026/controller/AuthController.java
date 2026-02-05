package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.fromApp.NewUserDTO;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;
import ru.sicampus.bootcamp2026.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody NewUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> loginUser(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(authentication.getName()));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<String> isUserExists (@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("Пользователь (Email: " + user.getEmail() + ") существует в системе.");
    }

//    @GetMapping("/login") //TODO реализовать авторизацию и получение данных пользователя
}
