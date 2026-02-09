package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitations")
@Getter
@Setter
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meet_id", nullable = false)
    private Meet meet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, ACCEPTED, DECLINED

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}