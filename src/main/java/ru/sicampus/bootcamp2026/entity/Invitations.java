package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Entity
@Table(name = "invitations")
public class Invitations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meetings meetingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users invitedUserId;

    @Column(name = "accepted")
    private boolean accepted;
}