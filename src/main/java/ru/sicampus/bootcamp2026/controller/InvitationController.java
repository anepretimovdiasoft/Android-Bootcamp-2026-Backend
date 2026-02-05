package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Invitation Controller", description = "Работа с уведомлениями")
@RequestMapping("/api/v1/invitation")
public class InvitationController {
}
