package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.request.UserUpdateRequest;
import ru.sicampus.bootcamp2026.dto.response.ApiResponse;
import ru.sicampus.bootcamp2026.dto.response.PageResponse;
import ru.sicampus.bootcamp2026.dto.response.UserResponse;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.Mapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> usersPage = userService.getAllUsers(pageable);

        PageResponse<UserResponse> response = Mapper.toUserPageResponse(usersPage);

        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse response = Mapper.toUserResponse(user);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserResponse response = Mapper.toUserResponse(user);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPosition(request.getPosition());

        User createdUser = userService.createUser(user);
        UserResponse response = Mapper.toUserResponse(createdUser);

        return ResponseEntity.ok(ApiResponse.success("User created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        User user = userService.getUserById(id);

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getPhotoUrl() != null) {
            user.setPhotoUrl(request.getPhotoUrl());
        }
        if (request.getPosition() != null) {
            user.setPosition(request.getPosition());
        }

        User updatedUser = userService.updateUser(id, user);
        UserResponse response = Mapper.toUserResponse(updatedUser);

        return ResponseEntity.ok(ApiResponse.success("User updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }

//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse> searchUsers(
//            @RequestParam String query,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<User> usersPage = userService.searchUsers(query, pageable);
//
//        PageResponse<UserResponse> response = Mapper.toUserPageResponse(usersPage);
//
//        return ResponseEntity.ok(ApiResponse.success("Search results", response));
//    }

}