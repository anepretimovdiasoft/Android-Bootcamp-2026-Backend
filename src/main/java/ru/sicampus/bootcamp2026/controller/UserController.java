package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.web.dto.user.UserDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserMiniDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserUpdateDto;
import ru.sicampus.bootcamp2026.web.mappers.UserMapper;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Работа с пользователями")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение UserDto по id")
    public UserDto getById(@PathVariable Long id) {
        return UserMapper.toDto(userService.getById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск всех подходящих пользователей по имени, фамилии, должности и отделу")
    public Page<UserMiniDto> search(
            @RequestParam("search") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.search(search, pageable).map(UserMapper::toMiniDto);
    }

    @PostMapping
    @Operation(summary = "Обновление данных пользователя")
    public UserDto update(@Validated @RequestBody UserUpdateDto userUpdateDto) {
        return UserMapper.toDto(userService.update(userUpdateDto));
    }

}
