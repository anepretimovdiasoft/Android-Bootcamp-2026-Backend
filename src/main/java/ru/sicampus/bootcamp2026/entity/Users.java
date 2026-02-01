package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Users {
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

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meetings> createdMeetings;
    // С неронки ↓↓↓↓
    // Связь: пользователь -> приглашения, которые он создал
    @OneToMany(mappedBy = "invitationCreator", cascade = CascadeType.ALL)
    private List<MeetingParticipants> createdInvitations;

    // Связь: пользователь -> приглашения, на которые его пригласили
    @OneToMany(mappedBy = "invitedUser", cascade = CascadeType.ALL)
    private List<MeetingParticipants> receivedInvitations;
}
