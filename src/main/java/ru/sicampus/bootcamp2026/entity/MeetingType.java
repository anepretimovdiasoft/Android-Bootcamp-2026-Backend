package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meeting_types")
public class MeetingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;
}