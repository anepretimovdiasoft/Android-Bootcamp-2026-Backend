package ru.examle.edu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.examle.edu.entity.Invitation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationUpdateRequest {
    @NotNull(message = "Status is required")
    private Invitation.InvitationStatus status;
}
