package ru.sicampus.bootcamp2026.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "meetings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime startsAt;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime endsAt;
}