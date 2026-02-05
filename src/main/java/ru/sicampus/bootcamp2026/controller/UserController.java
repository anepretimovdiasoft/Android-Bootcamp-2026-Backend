package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.web.dto.user.UserDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserMiniDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserUpdateDto;
import ru.sicampus.bootcamp2026.web.mappers.UserMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Работа с пользователями")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение UserDto по id")
    public UserDto getById(@PathVariable Long id) {
        return UserMapper.toDto(userService.getById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск всех подходящих пользователей по имени, фамилии, должности и отделу")
    public List<UserMiniDto> search(@RequestParam("search") String search) {
        // Позже добавлю пагинацию

        return UserMapper.toMiniDtoList(userService.search(search));
    }

    @PostMapping
    @Operation(summary = "Обновление данных пользователя")
    public UserDto update(@RequestBody UserUpdateDto userUpdateDto) {
        return UserMapper.toDto(userService.update(userUpdateDto));
    }

}
