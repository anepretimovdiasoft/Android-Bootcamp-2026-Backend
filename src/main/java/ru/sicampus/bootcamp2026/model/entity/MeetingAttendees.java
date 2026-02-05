package ru.sicampus.bootcamp2026.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sicampus.bootcamp2026.model.enums.UserStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meeting_attendees")
@Entity
public class MeetingAttendees {

    @EmbeddedId
    private MeetingAttendeesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id")
    private Meetings meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;


}
