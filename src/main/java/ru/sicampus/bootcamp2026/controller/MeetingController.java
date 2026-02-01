package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    /*
        TODO: Внедрить сервис(ы) позже
    */
    private final MeetingService meetingService;

    /**
     * Получение всех встреч текущего пользователя
     */
    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getUserMeetings(
            @RequestParam(required = false) String status
    ) {
        /*
            TODO: Получить текущего пользователя и вернуть его встречи
        */
        throw new UnsupportedOperationException("Метод getUserMeetings еще не реализован");
    }

    /**
     * Получение встречи по ID
     */
    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponse> getMeetingById(@PathVariable UUID meetingId) {
        return ResponseEntity.ok(meetingService.getMeetingById(meetingId));
    }

    /**
     * Создание новой встречи
     */
    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(@Valid @RequestBody CreateMeetingRequest request) {
        /*
            TODO: Получить текущего пользователя и создать встречу
        */
        throw new UnsupportedOperationException("Метод createMeeting еще не реализован");
    }

    /**
     * Отмена встречи
     */
    @PutMapping("/{meetingId}/cancel")
    public ResponseEntity<MeetingResponse> cancelMeeting(@PathVariable UUID meetingId) {
        /*
            TODO: Получить текущего пользователя и отменить встречу
        */
        throw new UnsupportedOperationException("Метод cancelMeeting еще не реализован");
    }

    /**
     * Удаление встречи (только для организатора)
     */
    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable UUID meetingId) {
        /*
            TODO: Получить текущего пользователя и удалить встречу
        */
        throw new UnsupportedOperationException("Метод deleteMeeting еще не реализован");
    }

}
