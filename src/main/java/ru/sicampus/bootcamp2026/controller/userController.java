package ru.sicampus.bootcamp2026.controller;

import ru.sicampus.bootcamp2026.dto.userDTO;
import ru.sicampus.bootcamp2026.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/USER")
@RequiredArgsConstructor
public class userController {
    private final userService userService;

    @PostMapping
    public ResponseEntity<userDTO> createUser(@Valid @RequestBody userDTO userDTO) {
        userDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<userDTO> getUserById(@PathVariable Long id) {
        userDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<userDTO>> getAllUsers() {
        List<userDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<userDTO> updateUser(@PathVariable Long id, @Valid @RequestBody userDTO userDTO) {
        userDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}