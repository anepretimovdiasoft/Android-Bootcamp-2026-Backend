package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.service.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable long id) {
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UserRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(dto));
    }

    public ResponseEntity<UsersDTO> login(Authentication authentication) {
        return ResponseEntity.ok(usersService.getUserByUsername(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable long id, @RequestBody UsersDTO usersDTO) {
        return ResponseEntity.ok(usersService.updateUser(id, usersDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<String> getUserByUsername(@PathVariable String username) {
        UsersDTO dto = usersService.getUserByUsername(username);
        return ResponseEntity.ok().body("User " + dto.getUsername() + " is registered");
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UsersDTO>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(usersService.getAllUsersPaginated(pageable));
    }

    @GetMapping("/login")
    public ResponseEntity<String> checkAuth() {
        return ResponseEntity.ok("Auth completed");
    }
}