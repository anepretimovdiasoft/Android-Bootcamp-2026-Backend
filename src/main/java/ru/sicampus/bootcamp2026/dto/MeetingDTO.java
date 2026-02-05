package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String ownerName;
    private Long acceptedInvitations;
    private Long totalInvitations;
}