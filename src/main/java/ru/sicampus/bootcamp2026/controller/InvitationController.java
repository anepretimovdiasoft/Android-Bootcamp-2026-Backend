package ru.sicampus.bootcamp2026.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.MeetingService;
import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
@Tag(name = "Приглашения", description = "Управление приглашениями на встречи")
public class InvitationController {
    private final MeetingService meetingService;

    @Operation(summary = "Список приглашений", description = "Возвращает список приглашений для пользователя")
    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getList(
            @Parameter(in = ParameterIn.HEADER, description = "ID текущего пользователя", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        return ResponseEntity.ok(meetingService.getInvitations(userId));
    }

    @Operation(summary = "Ответить на приглашение", description = "Принимает (ACCEPTED) или отклоняет (DECLINED) приглашение на встречу")
    @PostMapping("/{id}/respond")
    public ResponseEntity<ApiResponseDTO> respond(
            @Parameter(in = ParameterIn.HEADER, description = "ID текущего пользователя", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @Parameter(description = "ID приглашения", example = "105")
            @PathVariable Long id, @RequestBody InvitationResponseDTO req) {
        meetingService.respondToInvitation(userId, id, req.getStatus());
        return ResponseEntity.ok(new ApiResponseDTO("Ответ сохранен"));
    }
}