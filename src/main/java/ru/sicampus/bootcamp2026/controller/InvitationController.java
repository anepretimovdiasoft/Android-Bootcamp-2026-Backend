package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.web.dto.invitation.InvitationDto;
import ru.sicampus.bootcamp2026.web.dto.invitation.InvitationRespondDto;
import ru.sicampus.bootcamp2026.web.mappers.InvitationMapper;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Invitation Controller", description = "Работа с уведомлениями")
@RequestMapping("/api/v1/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    @Operation(summary = "Получение списка активных приглашений для авторизованного пользователя")
    public List<InvitationDto> userInvitations() {
        return InvitationMapper.toDtoList(invitationService.userInvitations());
    }

    @PutMapping("/respond")
    @Operation(summary = "Ответ пользователя на приглашение")
    public InvitationDto respondToInvitation(@Validated @RequestBody InvitationRespondDto invitationRespondDto) {
        return InvitationMapper.toDto(invitationService.respondToInvitation(invitationRespondDto));
    }

}
