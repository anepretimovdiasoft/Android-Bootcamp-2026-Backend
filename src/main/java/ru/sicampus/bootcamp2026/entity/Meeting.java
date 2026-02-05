package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_hour")
    private int startHour;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

}
