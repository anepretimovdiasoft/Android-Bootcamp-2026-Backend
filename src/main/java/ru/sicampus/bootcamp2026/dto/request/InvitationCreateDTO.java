package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitationCreateDTO {
    @NotNull
    private long meetingId;

    @NotNull
    private long userId;
}
