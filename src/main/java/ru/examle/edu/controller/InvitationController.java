package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.InvitationDTO;
import ru.examle.edu.service.InvitationService;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    public Page<InvitationDTO> getAllInvitations(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        return invitationService.getAllInvitations(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public InvitationDTO getInvitationById(@PathVariable Long id) {
        return invitationService.getInvitationById(id);
    }

    @PostMapping
    public InvitationDTO createInvitation(@RequestBody InvitationDTO invitationDTO) {
        return invitationService.createInvitation(invitationDTO);
    }

    @PutMapping("/{id}")
    public InvitationDTO updateInvitation(@PathVariable Long id, @RequestBody InvitationDTO invitationDTO) {
        return invitationService.updateInvitation(id, invitationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
    }
}
