package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.request.FreeTimeRequest;
import ru.sicampus.bootcamp2026.dto.response.FreeTimeResponse;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.User;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    /**
     * Получение всех встреч текущего пользователя
     */
    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getUserMeetings(
            @AuthenticationPrincipal User currentUser
    ) {
        List<MeetingResponse> meetings = meetingService.getUserMeetings(currentUser.getId());
        return ResponseEntity.ok(meetings);
    }

    /**
     * Получение встречи по ID
     */
    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponse> getMeetingById(@PathVariable UUID meetingId) {
        MeetingResponse meeting = meetingService.getMeetingById(meetingId);
        return ResponseEntity.ok(meeting);
    }

    /**
     * Создание новой встречи
     */
    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(
            @Valid @RequestBody CreateMeetingRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        MeetingResponse meeting = meetingService.createMeeting(
                currentUser.getId(),
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(meeting);
    }

    /**
     * Отмена встречи
     */
    @PutMapping("/{meetingId}/cancel")
    public ResponseEntity<MeetingResponse> cancelMeeting(
            @PathVariable UUID meetingId,
            @AuthenticationPrincipal User currentUser
    ) {
        MeetingResponse meeting = meetingService.cancelMeeting(
                currentUser.getId(),
                meetingId
        );
        return ResponseEntity.ok(meeting);
    }

    /**
     * Удаление встречи (только для организатора)
     */
    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(
            @PathVariable UUID meetingId,
            @AuthenticationPrincipal User currentUser
    ) {
        meetingService.deleteMeeting(currentUser.getId(), meetingId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получение свободного времени для пользователей
     */
    @PostMapping("/freeTime")
    public ResponseEntity<FreeTimeResponse> getFreeTime(
            @Valid @RequestBody FreeTimeRequest request
    ) {
        List<FreeTimeResponse.FreeTimeSlot> slots = meetingService.findFreeTimeSlots(
                request.getUserIds(),
                60
        );

        FreeTimeResponse response = FreeTimeResponse.builder()
                .startEndTime(slots)
                .build();

        return ResponseEntity.ok(response);
    }
}
