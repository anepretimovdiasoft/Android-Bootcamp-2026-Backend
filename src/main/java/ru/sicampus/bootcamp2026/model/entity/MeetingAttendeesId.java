package ru.sicampus.bootcamp2026.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MeetingAttendeesId implements Serializable {

    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "user_id")
    private Long userId;
}
