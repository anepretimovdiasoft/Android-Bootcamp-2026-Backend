package ru.sicampus.bootcamp2026.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.dto.UsersRegisterDTO;
import ru.sicampus.bootcamp2026.service.UsersService;
import ru.sicampus.bootcamp2026.service.impl.UsersServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public List<UsersDTO> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUsersById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getUsersById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> createUsers(@RequestBody UsersRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUsers(dto));
    }

    @GetMapping("/login")
    public ResponseEntity<UsersDTO> login(Authentication authentication) {
        return ResponseEntity.ok(usersService.getUsersByLogin(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUsers(@PathVariable Long id, @RequestBody UsersDTO dto) {
        return ResponseEntity.ok(usersService.updateUsers(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
        usersService.deleteUsers(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<String> getByLogin(@PathVariable String login){
        UsersDTO usersDTO = usersService.getUsersByLogin(login);
        return ResponseEntity.ok("User" +usersDTO.getLogin() + " is registered");
    }
}
