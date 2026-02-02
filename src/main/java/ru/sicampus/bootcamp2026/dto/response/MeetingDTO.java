package ru.sicampus.bootcamp2026.dto.response;

import ru.sicampus.bootcamp2026.dto.response.UserResponseDTO;

import java.time.Instant;

public class MeetingDTO {
    private long id;
    private UserResponseDTO organizer;
    private String title;
    private String description;
    private Instant timeStart;
    private Instant timeEnd;
    private Instant createdAt;
    private Instant updatedAt;
}
