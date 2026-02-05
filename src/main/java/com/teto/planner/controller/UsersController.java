package com.teto.planner.controller;

import com.teto.planner.dto.CreateUserRequest;
import com.teto.planner.dto.UpdateUserRequest;
import com.teto.planner.dto.UserDto;
import com.teto.planner.dto.UsersPage;
import com.teto.planner.service.UserService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UsersPage listUsers(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "50") int size,
            @RequestParam(value = "busyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate busyDate,
            @RequestParam(value = "includeLoad", defaultValue = "true") boolean includeLoad
    ) {
        LocalDate date = busyDate != null ? busyDate : LocalDate.now();
        return userService.listUsers(query, date, includeLoad, page, size);
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request.login(), request.name(), request.password(), request.telegramNick());
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto patchUser(@PathVariable UUID userId, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request.name(), request.telegramNick());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{userId}/avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAvatar(@PathVariable UUID userId) {
        var user = userService.findUser(userId);
        if (user.getAvatarBytes() == null || user.getAvatarBytes().length == 0) {
            return ResponseEntity.notFound().build();
        }
        MediaType contentType = user.getAvatarContentType() != null
                ? MediaType.parseMediaType(user.getAvatarContentType())
                : MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(user.getAvatarBytes());
    }

}
