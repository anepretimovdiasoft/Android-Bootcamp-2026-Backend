package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    // Валидация данных происходит в dto

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    // Хеши BCrypt имеют длину ~68 символов
    // https://stackoverflow.com/questions/75628635/what-should-be-password-field-length-in-db-when-using-spring-security
    @Column(name = "password", length = 80, nullable = false)
    private String password;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "second_name", length = 20, nullable = false)
    private String secondName;

    @Column(name = "description", length = 140)
    private String description;

    @Column(name = "position", length = 30)
    private String position;

    @Column(name = "department", length = 30)
    private String department;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @ToString.Exclude // Когда в логах вызывается .toString() возникает циклическая зависимость и StackOverflow
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
