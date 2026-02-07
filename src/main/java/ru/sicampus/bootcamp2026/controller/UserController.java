package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserRegisterDto;
import ru.sicampus.bootcamp2026.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRegisterDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping("/vi/login")
    public ResponseEntity<UserDto> login(Authentication authentication){
        return ResponseEntity.ok(userService.getUserByLogin(authentication.getName()));
    }

    @PutMapping("/v1/me")
    public ResponseEntity<UserDto> updateUser(Authentication auth,
                                              @RequestBody UserDto userDto){
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
