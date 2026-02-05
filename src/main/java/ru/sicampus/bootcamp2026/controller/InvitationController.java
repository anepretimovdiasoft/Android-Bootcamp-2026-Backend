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

    @PutMapping("/{id}")
    public ResponseEntity<InvitationDTO> updateInvitation(@PathVariable Long id, @RequestBody InvitationDTO dto) {
        return ResponseEntity.ok(invitationService.updateInvitation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}
