package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(dto));
    }

//    @DeleteMapping("/{id}") //TODO доделать удаление (при удалении пользователя удаляется и мероприятие)
//    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
