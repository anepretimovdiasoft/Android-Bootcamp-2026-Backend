package ru.sicampus.bootcamp2026.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "target", nullable = false)
    private Users target;

    @ManyToOne
    @JoinColumn(name = "meeting", nullable = false)
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
