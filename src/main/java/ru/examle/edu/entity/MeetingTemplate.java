package ru.examle.edu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.examle.edu.entity.enums.MeetingPriority;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_templates")
@Getter
@Setter
public class MeetingTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "title_pattern")
    private String titlePattern;

    @Column(name = "default_duration_hours", nullable = false)
    private Integer defaultDurationHours = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_priority", nullable = false, length = 20)
    private MeetingPriority defaultPriority = MeetingPriority.MEDIUM;

    @Column(name = "required_participants", columnDefinition = "TEXT")
    private String requiredParticipants;

    @Column(name = "suggested_rooms", columnDefinition = "TEXT")
    private String suggestedRooms;

    @Column(name = "agenda_template", columnDefinition = "TEXT")
    private String agendaTemplate;

    @Column(name = "is_company_template", nullable = false)
    private boolean isCompanyTemplate = true;

    @Column(length = 150)
    private String department;

    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
