package ru.sicampus.bootcamp2026.web.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;

@Data
@Builder
@Schema(description = "Используется для отправки статуса ответа")
public class InvitationRespondDto {

    @Schema(description = "Id приглашения", example = "1")
    @NotNull(message = "Id приглашения не может быть пустым.")
    private long invitationId;

    @Schema(description = "Статус", example = "ACCEPTED или DECLINED")
    @NotNull(message = "Статус приглашения не может быть пустым.")
    private InvitationStatus status;

}
