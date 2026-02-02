package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/{userId}/meetings/{meetingId}/invitation")
    public InvitationDecisionRequest decideInvitation(
            @PathVariable long userId,
            @PathVariable long meetingId,
            @Valid @RequestBody InvitationDecisionRequest req
    ) {
        return service.decideInvitation(userId, meetingId, req);
    }

    // ✅ один эндпоинт по статусу
    @GetMapping("/{userId}/meetings")
    public List<MeetingResponse> meetingsByStatus(
            @PathVariable long userId,
            @RequestParam String status
    ) {
        return service.listMeetingsByStatus(userId, status);
    }

    @GetMapping
    public List<UserResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
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