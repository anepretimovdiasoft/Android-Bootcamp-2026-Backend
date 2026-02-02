package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class InvitationDTO {
    Long id;
    private String status;
    private String message;
}
