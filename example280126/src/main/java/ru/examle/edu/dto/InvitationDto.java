package ru.examle.edu.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import ru.examle.edu.entity.Invitation.InvitationStatus;
import java.time.LocalDateTime;

@Data
public class InvitationDto {
    private Long id;
    private MeetingSimpleDto meeting;
    private PersonSimpleDto person;
    private InvitationStatus status;
    private LocalDateTime responseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

