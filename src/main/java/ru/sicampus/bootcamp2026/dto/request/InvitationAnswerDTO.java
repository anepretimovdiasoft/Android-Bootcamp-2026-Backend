package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitationAnswerDTO {
    @NotNull
    private boolean accepted;
}
