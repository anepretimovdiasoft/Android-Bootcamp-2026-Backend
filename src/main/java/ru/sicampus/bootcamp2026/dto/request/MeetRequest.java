package ru.sicampus.bootcamp2026.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MeetRequest {
    private String title;
    private String description;
    private LocalDate meetDate;
    private LocalTime meetTime;
    private Long organizerId;
    private List<Long> inviteeIds;
}