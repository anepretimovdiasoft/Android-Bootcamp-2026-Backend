package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.UserDtos.*;
import ru.sicampus.bootcamp2026.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("/{userId}/meetings/{meetingId}/invitation")
    public InvitationDecisionRequest decideInvitation(@PathVariable long userId, @PathVariable long meetingId, @Valid @RequestBody InvitationDecisionRequest req) {
        return service.decideInvitation(userId, meetingId, req);
    }

    @GetMapping("/{userId}/meetings")
    public Page<MeetingResponse> meetingsByStatus(
            @PathVariable long userId,
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listMeetingsByStatus(userId, status, pageable);
    }

    @GetMapping("/paginated")
    public Page<UserResponse> getAllPersonPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllUserPaginated(search, pageable);
    }

    @GetMapping("/login")
    public UserResponse login(Authentication authentication) {
        return service.getByLogin(authentication.getName());
    }

    @GetMapping
    public List<UserResponse> list() { return service.list(); }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable long id) { return service.get(id); }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable long id, @Valid @RequestBody UpdateUserRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) { service.delete(id); }
}