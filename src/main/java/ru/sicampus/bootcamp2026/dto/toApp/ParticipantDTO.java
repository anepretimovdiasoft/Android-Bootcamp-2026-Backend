package ru.sicampus.bootcamp2026.dto.toApp;

import lombok.Data;

@Data
public class ParticipantDTO {
    private Long id;
    private String fullName;
    private String status;
}
