package ru.sicampus.bootcamp2026.dto.request;

import lombok.Data;

@Data
public class InvitationRequest {
    private Long meetId;
    private Long userId;
}