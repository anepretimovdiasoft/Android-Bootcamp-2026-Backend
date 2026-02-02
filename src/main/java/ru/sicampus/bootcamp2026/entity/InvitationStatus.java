package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invitation_statuses")
public class InvitationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_name", nullable = false, unique = true)
    private String statusName;
}