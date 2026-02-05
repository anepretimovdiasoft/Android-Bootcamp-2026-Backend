package ru.sicampus.bootcamp2026.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meeting_statuses")
public class MeetingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_name", nullable = false, unique = true)
    private String statusName;
}