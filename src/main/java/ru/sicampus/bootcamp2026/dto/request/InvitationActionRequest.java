package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationActionRequest {

    @NotNull(message = "Статус необходим")
    private ParticipantStatus status;
}
