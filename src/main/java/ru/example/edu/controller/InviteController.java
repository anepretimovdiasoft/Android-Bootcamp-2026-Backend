package ru.example.edu.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.edu.dto.InviteDTO;
import ru.example.edu.dto.InviteUpdateDTO;
import ru.example.edu.service.InviteService;

@RestController
@RequestMapping("api/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;

    @PutMapping("/{id}")
    public ResponseEntity<InviteDTO> updateInvite(@PathVariable Long id, @RequestBody InviteUpdateDTO dto) {
        return ResponseEntity.ok(inviteService.updateInvite(id, dto));
    }
}
