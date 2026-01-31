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
    @MapsId("meetingId")//связывает поле meetingId составного ключа с полем meetingId сущности Meeting
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meetingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")//тоже самое но для User
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipantStatus status = ParticipantStatus.PENDING;

}
