package ru.sicampus.bootcamp2026.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class MeetingParticipantId { // Промежуточная сущность описывающая составной ключ
    private UUID meetingId;
    private UUID userId;
}
