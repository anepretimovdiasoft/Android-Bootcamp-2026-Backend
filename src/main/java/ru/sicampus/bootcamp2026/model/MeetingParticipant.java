package ru.sicampus.bootcamp2026.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meeting_participants")
public class MeetingParticipant {

    @EmbeddedId
    private MeetingParticipantId id = new MeetingParticipantId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipantStatus status = ParticipantStatus.PENDING;
}
