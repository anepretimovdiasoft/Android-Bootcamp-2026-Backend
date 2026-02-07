package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Entity
@Table(name = "person")
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name="username")
	private String username;

    @Column(name ="password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id")
    )
    private Set<Authority> authorities;
}
