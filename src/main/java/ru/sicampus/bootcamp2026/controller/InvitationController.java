package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.InvitationActionRequest;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    /*
        TODO: Внедрить сервис(ы) позже
    */

    /**
     * Получение списка активных приглашений текущего пользователя
     */
    @GetMapping
    public ResponseEntity<List<InvitationResponse>> getInvitations() {
        /*
            TODO: Получить текущего пользователя и вернуть его приглашения
        */
        throw new UnsupportedOperationException("Метод getInvitations еще не реализован");
    }

    /**
     * Получение приглашения по ID встречи, детали приглашения
     */
    @GetMapping("/{meetingId}")
    public ResponseEntity<InvitationResponse> getInvitationDetails(@PathVariable UUID meetingId) {
        /*
            TODO: Получить текущего пользователя и вернуть приглашение
        */
        throw new UnsupportedOperationException("Метод getInvitationById еще не реализован");
    }

    /**
     * Принять или отклонить приглашение
     */
    @PutMapping("/{meetingId}/respond")
    public ResponseEntity<InvitationResponse> respondToInvitation(
            @PathVariable UUID meetingId,
            @Valid @RequestBody InvitationActionRequest request) {
        /*
            TODO: Получить текущего пользователя и обработать ответ на приглашение
        */
        throw new UnsupportedOperationException("Метод respondToInvitation еще не реализован");
    }
}
