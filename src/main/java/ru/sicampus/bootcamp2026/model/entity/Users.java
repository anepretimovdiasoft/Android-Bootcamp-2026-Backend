package ru.sicampus.bootcamp2026.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "position")
    private String position;

    // Встречи, которые пользователь создал
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Meetings> createdMeetings = new ArrayList<>();

    // Встречи, где пользователь участник
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MeetingAttendees> meetings = new ArrayList<>();
}
