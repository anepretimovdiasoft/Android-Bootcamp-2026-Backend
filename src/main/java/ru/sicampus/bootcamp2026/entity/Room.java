package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    private Integer capacity;

    @Column(name = "location_desc")
    private String locationDesc;
}