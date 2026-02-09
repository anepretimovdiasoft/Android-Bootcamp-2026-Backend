package ru.sicampus.bootcamp2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Переопределяет e&hc только для полей с аннотацией @EqualsAndHashCode.Include
@Table(name = "meetings")
public class Meeting {

    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) // У одного пользователя может быть множество встреч
    @JoinColumn(name = "organizer_id")
    private User organizer_id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 2048)
    @Column(name = "body", nullable = true)
    private String description;

    @Column(name = "location", nullable = true)
    private String location;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "meeting_status",nullable = false)
    private MeetingStatus meetingStatus = MeetingStatus.SCHEDULED;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "meeting",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<MeetingParticipant> meetingParticipants = new ArrayList<>();
}
