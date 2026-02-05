package ru.sicampus.bootcamp2026.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_meeting")
@Data

public class UserMeeting {

    @EmbeddedId
    private UserMeetingId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("meetingId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(name = "status", nullable = false, length = 32)
    private String status;
}