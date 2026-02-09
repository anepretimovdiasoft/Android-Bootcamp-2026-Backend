package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "meets")
@Getter
@Setter
public class Meet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "meet_date", nullable = false)
    private LocalDate meetDate;

    @Column(name = "meet_time", nullable = false)
    private LocalTime meetTime;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;
}