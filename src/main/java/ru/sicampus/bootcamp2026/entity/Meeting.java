package ru.sicampus.bootcamp2026.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime start;

    private int duration;

    private String place;

    private String theme;

    private String description;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private Users creator;

    @ToString.Exclude
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<UsersMeeting> members;
}
