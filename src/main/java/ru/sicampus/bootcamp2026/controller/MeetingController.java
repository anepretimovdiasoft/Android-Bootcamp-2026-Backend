package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.request.FreeTimeRequest;
import ru.sicampus.bootcamp2026.dto.response.FreeTimeResponse;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.CustomUserDetails;
import ru.sicampus.bootcamp2026.model.MeetingStatus;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    /**
     * Получение встреч текущего пользователя с пагинацией.
     *
     * Примеры:
     *  - GET /api/v1/meetings?page=0&size=20&sort=startTime,desc
     *  - GET /api/v1/meetings?status=SCHEDULED&page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<MeetingResponse>> getUserMeetings(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam(required = false) MeetingStatus status,
            Pageable pageable
    ) {
        UUID currentUserId = currentUser.user().getId();

        Page<MeetingResponse> meetings = (status == null)
                ? meetingService.getUserMeetings(currentUserId, pageable)
                : meetingService.getMeetingsByStatus(currentUserId, status, pageable);

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
    @PostMapping()
    public ResponseEntity<MeetingResponse> createMeeting(
            @Valid @RequestBody CreateMeetingRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        MeetingResponse meeting = meetingService.createMeeting(
                currentUser.user().getId(),
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
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        MeetingResponse meeting = meetingService.cancelMeeting(
                currentUser.user().getId(),
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
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        meetingService.deleteMeeting(currentUser.user().getId(), meetingId);
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
