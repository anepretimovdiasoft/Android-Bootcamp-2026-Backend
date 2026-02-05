package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meeting_participants")
public class MeetingParticipants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meetings meeting;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private Users invitationCreator;


    @ManyToOne
    @JoinColumn(name = "invitedUser", nullable = false)
    private Users invitedUser;

    @Column(name = "accepted_invitation")
    private Boolean acceptedInvitation;
}