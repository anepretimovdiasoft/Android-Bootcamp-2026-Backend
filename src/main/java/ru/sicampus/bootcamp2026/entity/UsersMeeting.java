package ru.sicampus.bootcamp2026.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="users_meeting")
public class UsersMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private Users member;

    @ManyToOne
    @JoinColumn(name = "meeting", nullable = false)
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    private UsersMeetingStatus status;
}
