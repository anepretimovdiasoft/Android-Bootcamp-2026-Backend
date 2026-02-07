package ru.sicampus.bootcamp2026.api;

import jakarta.validation.Valid;
import ru.sicampus.bootcamp2026.api.dto.InvitationRequest;
import ru.sicampus.bootcamp2026.api.dto.InvitationRespondRequest;
import ru.sicampus.bootcamp2026.api.dto.InvitationResponse;
import ru.sicampus.bootcamp2026.api.dto.InvitationWithMeetingResponse;
import ru.sicampus.bootcamp2026.api.dto.MeetingResponse;
import ru.sicampus.bootcamp2026.domain.Invitation;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.service.InvitationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping
    public Page<InvitationResponse> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return invitationService.list(pageable).map(InvitationController::toResponse);
    }

    @GetMapping("/my/with-meeting")
    public Page<InvitationWithMeetingResponse> myWithMeeting(
        @AuthenticationPrincipal Person person,
        @RequestParam(defaultValue = "PENDING") InvitationStatus status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return invitationService.listForInvitee(person.getId(), status, pageable)
            .map(InvitationController::toWithMeetingResponse);
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

    @PatchMapping("/{id}/respond")
    public InvitationResponse respond(
        @PathVariable Long id,
        @AuthenticationPrincipal Person person,
        @Valid @RequestBody InvitationRespondRequest request
    ) {
        return toResponse(invitationService.respond(id, person.getId(), request.status()));
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

    private static InvitationWithMeetingResponse toWithMeetingResponse(Invitation invitation) {
        return new InvitationWithMeetingResponse(
            invitation.getId(),
            invitation.getInvitee().getId(),
            invitation.getStatus(),
            invitation.getRespondedAt(),
            invitation.getCreatedAt(),
            toMeetingResponse(invitation.getMeeting())
        );
    }

    private static MeetingResponse toMeetingResponse(Meeting meeting) {
        return new MeetingResponse(
            meeting.getId(),
            meeting.getOrganizer().getId(),
            meeting.getTitle(),
            meeting.getDescription(),
            meeting.getStartAt(),
            meeting.getEndAt(),
            meeting.getCreatedAt()
        );
    }
}
