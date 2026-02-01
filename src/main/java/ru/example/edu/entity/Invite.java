package ru.example.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invite")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "agree")
    private Boolean agree;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Person participant;

    @ManyToOne
    @JoinColumn(name = "meetup_id", nullable = false)
    private Meetup meetup;
}
