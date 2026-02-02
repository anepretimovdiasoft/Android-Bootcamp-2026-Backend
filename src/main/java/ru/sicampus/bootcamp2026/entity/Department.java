package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dept_name", nullable = false, unique = true)
    private String deptName;
}