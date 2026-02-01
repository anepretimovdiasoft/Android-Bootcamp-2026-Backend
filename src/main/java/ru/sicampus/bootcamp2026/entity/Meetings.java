package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "meetings")
public class Meetings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "topic")
    private String topic;

    @Column (name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private Users creator;

    @Column (name = "date")
    private String date;

    @Column (name = "timeStart")
    private String timeStart;

    @Column (name = "duration")
    private int duration;

    @Column (name = "place")
    private String place;

    @Column (name = "status")
    private String status;

    // С неронки ↓↓↓↓
    // Связь: одно собрание -> много участников
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<MeetingParticipants> participants;
}
