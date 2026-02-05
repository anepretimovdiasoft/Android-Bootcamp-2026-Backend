package ru.sicampus.bootcamp2026.api;

import jakarta.validation.Valid;
import java.util.List;
import ru.sicampus.bootcamp2026.api.dto.InvitationRequest;
import ru.sicampus.bootcamp2026.api.dto.InvitationResponse;
import ru.sicampus.bootcamp2026.domain.Invitation;
import ru.sicampus.bootcamp2026.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping
    public List<InvitationResponse> list() {
        return invitationService.list().stream().map(InvitationController::toResponse).toList();
    }

    @GetMapping("/{id}")
    public InvitationResponse get(@PathVariable Long id) {
        return toResponse(invitationService.get(id));
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> create(@Valid @RequestBody InvitationRequest request) {
        Invitation created = invitationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @PutMapping("/{id}")
    public InvitationResponse update(@PathVariable Long id, @Valid @RequestBody InvitationRequest request) {
        return toResponse(invitationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invitationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static InvitationResponse toResponse(Invitation invitation) {
        return new InvitationResponse(
            invitation.getId(),
            invitation.getMeeting().getId(),
            invitation.getInvitee().getId(),
            invitation.getStatus(),
            invitation.getRespondedAt(),
            invitation.getCreatedAt()
        );
    }
}
