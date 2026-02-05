package ru.example.edu.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "meetup")
public class Meetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "planner_id", nullable = false)
    private Person planner;

    @OneToMany(mappedBy = "meetup", cascade = CascadeType.ALL)
    private List<Invite> invites;
}
