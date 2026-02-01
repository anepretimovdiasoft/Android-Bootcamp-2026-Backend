package ru.example.edu.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MeetupDTO {
    private long id;
    private LocalDate date;
    private LocalTime time;
    private PersonWithInvitesShortDTO planner;
}

