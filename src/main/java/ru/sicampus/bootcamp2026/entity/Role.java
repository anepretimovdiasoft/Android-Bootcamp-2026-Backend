package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @ToString.Exclude  // Когда в логах вызывается .toString() возникает циклическая зависимость и StackOverflow
    private List<User> users;

}
