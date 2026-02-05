package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "department")
    private String departmentName;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "messenger_link", unique = true)
    private String messengerLink;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String Email;

    @ToString.Exclude
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> createdMeetings;

    @ToString.Exclude
    @OneToMany(mappedBy = "invitedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invites;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;
}
