package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "meeting_participants")
public class MeetingParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "invitation_status_id")
    private InvitationStatus invitationStatus;

    @Column(name = "is_required")
    private Boolean isRequired = true;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}