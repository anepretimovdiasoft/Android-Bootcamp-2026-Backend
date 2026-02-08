package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserRegisterDto;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/all")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return  ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/v1/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRegisterDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping("/v1/login")
    public ResponseEntity<UserDto> login(Authentication authentication){
        return ResponseEntity.ok(userService.getUserByLogin(authentication.getName()));
    }

    @PutMapping("/v1/me")
    public ResponseEntity<UserDto> updateUser(Authentication auth,
                                              @Valid @RequestBody UserDto userDto){
        String login = auth.getName();
        return ResponseEntity.ok(userService.updateUser(login, userDto));
    }

    @DeleteMapping("/v1/me")
    public ResponseEntity<Void> deleteUser(Authentication authentication){
        String login = authentication.getName();
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

}
