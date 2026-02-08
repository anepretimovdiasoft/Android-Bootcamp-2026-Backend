package ru.example.edu.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.edu.dto.InviteCreateDTO;
import ru.example.edu.dto.InviteDTO;
import ru.example.edu.dto.InviteUpdateDTO;
import ru.example.edu.dto.InviteWithMeetupDTO;
import ru.example.edu.service.InviteService;

import java.util.List;

@RestController
@RequestMapping("api/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;

    @PutMapping("/{id}")
    public ResponseEntity<InviteDTO> updateInvite(@PathVariable Long id, @RequestBody InviteUpdateDTO dto) {
        return ResponseEntity.ok(inviteService.updateInvite(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<InviteWithMeetupDTO>> getInvitesByParticipantId(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inviteService.getInvitesByParticipantId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<InviteDTO> createInvite(@RequestBody InviteCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inviteService.createInvite(dto));
    }
}
