package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee;

    @Column(name = "status")
    private int status;

    @Column(name = "invited_at")
    private LocalDateTime invitedAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}
