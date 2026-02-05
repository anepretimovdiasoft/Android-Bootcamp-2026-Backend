package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column (name = "name")
    private String name;

    @Column (name = "LASTNAME")
    private String lastName;

    @Column (name = "PHONENUMBER")
    private String phoneNumber;

    @Column (name = "login")
    private String login;

    @Column (name = "email")
    private String email;

    @Column (name = "password")
    private String password;

    @Column (name = "department")
    private String department;

    @Column (name = "position")
    private String position;

    @Column (name = "photo_url")
    private String photoUrl;

    @ToString.Exclude
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meetings> createdMeetings;
    // С неронки ↓↓↓↓
    // Связь: пользователь -> приглашения, которые он создал
    @ToString.Exclude
    @OneToMany(mappedBy = "invitationCreator", cascade = CascadeType.ALL)
    private List<MeetingParticipants> createdInvitations;

    // Связь: пользователь -> приглашения, на которые его пригласили
    @ToString.Exclude
    @OneToMany(mappedBy = "invitedUser", cascade = CascadeType.ALL)
    private List<MeetingParticipants> receivedInvitations;
    //-----------------------------------------------------
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
