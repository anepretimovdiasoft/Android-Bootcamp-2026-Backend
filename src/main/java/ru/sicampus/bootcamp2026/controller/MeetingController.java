package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.UpdateMeetingRequest;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService service;

    @GetMapping
    public List<MeetingResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public MeetingResponse get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    public MeetingResponse create(@Valid @RequestBody CreateMeetingRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public MeetingResponse update(@PathVariable long id, @Valid @RequestBody UpdateMeetingRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}