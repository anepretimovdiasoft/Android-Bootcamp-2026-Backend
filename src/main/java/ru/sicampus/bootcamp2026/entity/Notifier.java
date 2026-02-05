package ru.sicampus.bootcamp2026.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notifier")
public class Notifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "addressee", nullable = false)
    private Users addressee;

    @ManyToOne
    @JoinColumn(name = "meeting", nullable = false)
    private Meeting meeting;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String text;
}
