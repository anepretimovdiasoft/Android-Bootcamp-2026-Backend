package ru.sicampus.bootcamp2026.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.UserService;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Методы для работы с пользователями")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех зарегистрированных пользователей")
    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Найти пользователя по ID", description = "Возвращает данные пользователя по его уникальному идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@Parameter(description = "ID пользователя") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Создает нового пользователя в системе")
    @PostMapping("/register")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UserRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @Operation(summary = "Обновить профиль", description = "Обновляет данные существующего пользователя")
    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateProfile(
            @Parameter(description = "ID пользователя")
            @PathVariable Long id,
            @RequestBody UsersDTO dto) {
        return ResponseEntity.ok(userService.updateProfile(id, dto));
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя из базы данных по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID пользователя") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Найти по почте", description = "Поиск пользователя по адресу электронной почты")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsersDTO> getByEmail(@Parameter(description = "Почта") @PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @Operation(summary = "Авторизация пользователя", description = "Выполняет вход в аккаунт")
    @GetMapping("/login")
    public ResponseEntity<UsersDTO> login(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserByEmail(authentication.getName()));
    }

    @Operation(summary = "Получить всех пользователей",
            description = "Позволяет постранично загружать пользователей")
    @GetMapping("/paginated")
    public ResponseEntity<Page<UsersDTO>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
}