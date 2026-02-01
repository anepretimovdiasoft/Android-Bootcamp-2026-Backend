package ru.example.edu.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.edu.dto.MeetupShortDTO;
import ru.example.edu.dto.MeetupToCreateDTO;
import ru.example.edu.dto.MeetupWithInvitesDTO;
import ru.example.edu.dto.MeetupDTO;
import ru.example.edu.service.MeetupService;

import java.util.List;

@RestController
@RequestMapping("api/meetup")
@RequiredArgsConstructor
public class MeetupController {
    private final MeetupService meetupService;

    @GetMapping
    public List<MeetupWithInvitesDTO> getAllMeetups() {return meetupService.getAllMeetups();}

    @GetMapping("/{id}")
    public MeetupWithInvitesDTO getMeetupById(@PathVariable Long id) {
        return meetupService.getMeetupById(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MeetupDTO> createMeetup(@RequestBody MeetupToCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetupService.createMeetup(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetupShortDTO> updateMeetup(@PathVariable Long id, @RequestBody MeetupShortDTO dto) {
        return ResponseEntity.ok(meetupService.updateMeetup(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetupService.deleteMeetup(id);
        return ResponseEntity.noContent().build();
    }
}
