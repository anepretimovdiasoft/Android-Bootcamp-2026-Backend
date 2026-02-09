package ru.sicampus.bootcamp2026.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MeetResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate meetDate;
    private LocalTime meetTime;
    private UserResponse organizer;
}