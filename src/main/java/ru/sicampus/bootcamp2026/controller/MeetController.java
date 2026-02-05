package ru.sicampus.bootcamp2026.controller;

import ru.sicampus.bootcamp2026.dto.meetDTO;
import ru.sicampus.bootcamp2026.service.meetService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meets")
@RequiredArgsConstructor
public class MeetController {
    private final meetService meetService;

    @PostMapping
    public ResponseEntity<meetDTO> createMeet(@Valid @RequestBody meetDTO meetDTO) {
        meetDTO createdMeet = meetService.createMeet(meetDTO);
        return new ResponseEntity<>(createdMeet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<meetDTO> getMeetById(@PathVariable Long id) {
        meetDTO meetDTO = meetService.getMeetById(id);
        return ResponseEntity.ok(meetDTO);
    }

    @GetMapping
    public ResponseEntity<List<meetDTO>> getAllMeets() {
        List<meetDTO> meets = meetService.getAllMeets();
        return ResponseEntity.ok(meets);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<meetDTO>> getMeetsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<meetDTO> meets = meetService.getMeetsByDate(date);
        return ResponseEntity.ok(meets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<meetDTO> updateMeet(@PathVariable Long id, @Valid @RequestBody meetDTO meetDTO) {
        meetDTO updatedMeet = meetService.updateMeet(id, meetDTO);
        return ResponseEntity.ok(updatedMeet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeet(@PathVariable Long id) {
        meetService.deleteMeet(id);
        return ResponseEntity.noContent().build();
    }
}