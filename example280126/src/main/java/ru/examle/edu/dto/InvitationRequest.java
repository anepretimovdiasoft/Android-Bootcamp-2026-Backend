package ru.examle.edu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.examle.edu.entity.Invitation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequest {
    @NotNull(message = "Meeting ID is required")
    private Long meetingId;

    @NotNull(message = "Person ID is required")
    private Long personId;

    private Invitation.InvitationStatus status;
}
