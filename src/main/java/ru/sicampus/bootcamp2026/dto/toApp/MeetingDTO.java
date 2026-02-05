package ru.sicampus.bootcamp2026.dto.toApp;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MeetingDTO {
    private Long id;
    private String title;
    private String description;
    private Long organizerId;
    private String organizerName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<ParticipantDTO> participants;
}
