package ru.sicampus.bootcamp2026.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sicampus.bootcamp2026.model.enums.MeetingsStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meetings")
@Entity
public class Meetings {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь: встреча имеет одного создателя
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Users creator;

    @Column(name = "title", nullable = false)
    private String title;

    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MeetingsStatus status;

    // участники встречи
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingAttendees> attendees = new ArrayList<>();
}
