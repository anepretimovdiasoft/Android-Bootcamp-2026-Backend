package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@RequestBody InvitationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationService.createInvitation(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationDTO> getInvitationById(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.getInvitationById(id));
    }

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
        return ResponseEntity.ok(invitationService.getAllInvitations());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<InvitationDTO>> getAllInvitationsPaginated(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(invitationService.getAllInvitationsPaginated(pageable));
    }

    @GetMapping("/unaccepted/paginated/by_userId/{userId}")
    public ResponseEntity<Page<InvitationDTO>> getAllUnacceptedInvitationsByUserIdPaginated(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(invitationService.getAllUnacceptedInvitationsByUserIdPaginated(userId, pageable));
    }

    @GetMapping("/by_userId/{userId}")
    public ResponseEntity<List<InvitationDTO>> getAllInvitationsByUserId(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(defaultValue = "1970-01-01") String since) {
        return ResponseEntity.ok(invitationService.getAllInvitationsByUserId(userId, since));
    }

    @GetMapping("/paginated/by_userId/{userId}")
    public ResponseEntity<Page<InvitationDTO>> getAllInvitationsByUserIdPaginated(@PathVariable(name = "userId") Long userId,
            @RequestParam(defaultValue = "1970-01-01") String since,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ResponseEntity.ok(invitationService.getAllInvitationsByUserIdPaginated(userId, since, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationDTO> updateInvitation(@PathVariable Long id, @RequestBody InvitationDTO dto) {
        return ResponseEntity.ok(invitationService.updateInvitation(id, dto));
    }

    @PatchMapping("/confirm/{id}")
    public ResponseEntity<InvitationDTO> confirmInvitation(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.confirmInvitationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}
