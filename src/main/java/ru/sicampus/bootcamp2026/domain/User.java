package ru.sicampus.bootcamp2026.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false, length = 255, unique = true)
    private String login;

    @Column(name = "role", nullable = false, length = 255)
    private String role;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

}