package ru.sicampus.bootcamp2026.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "job_titles")
public class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_name", nullable = false, unique = true)
    private String titleName;
}