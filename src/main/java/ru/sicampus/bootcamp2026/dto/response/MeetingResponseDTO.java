package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class MeetingResponseDTO {
    private long id;

    private UserResponseDTO organizer;

    private String title;

    private String descripption;

    private Instant timeStart;

    private Instant timeEnd;

    private Instant createdAt;

    private Instant updatedAt;
}
