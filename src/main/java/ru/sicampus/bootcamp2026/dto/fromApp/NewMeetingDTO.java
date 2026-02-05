package ru.sicampus.bootcamp2026.dto.fromApp;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class NewMeetingDTO {
    private Long organizerId;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Long> participantsId;
}
