package ru.sicampus.bootcamp2026.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    private String patronymic;

    private String position;

    @Column(unique = true)
    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meeting> meetingOwner;

    @ToString.Exclude
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL)
    private List<Invitation> invitation;

    @ToString.Exclude
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<UsersMeeting> myMeeting;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public String getPassword() {
        return hashPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
