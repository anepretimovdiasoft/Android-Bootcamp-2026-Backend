package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.UserDtos.CreateUserRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.InvitationDecisionRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.UpdateUserRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.UserResponse;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserService userService;

    @PatchMapping("/{userId}/meetings/{meetingId}/invitation")
    public InvitationDecisionRequest decideInvitation(
            @PathVariable long userId,
            @PathVariable long meetingId,
            @Valid @RequestBody InvitationDecisionRequest req
    ) {
        return service.decideInvitation(userId, meetingId, req);
    }

    @GetMapping("/{userId}/meetings")
    public List<MeetingResponse> meetingsByStatus(
            @PathVariable long userId,
            @RequestParam String status
    ) {
        return service.listMeetingsByStatus(userId, status);
    }

    @GetMapping("/login")
    public UserResponse login(Authentication authentication) {
        return userService.getByLogin(authentication.getName());
    }
    @GetMapping("/getByLogin/{login}")
    public ResponseEntity<String> getByLogin(@PathVariable String login) {
        UserResponse user = userService.getByLogin(login);
        return ResponseEntity.ok("User " + user.login() + " is registered");
    }

    @GetMapping
    public List<UserResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping("/register")
    public UserResponse create(@Valid @RequestBody CreateUserRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable long id, @Valid @RequestBody UpdateUserRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}