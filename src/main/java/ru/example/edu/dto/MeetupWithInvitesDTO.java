package ru.example.edu.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MeetupWithInvitesDTO {
    private long id;
    private LocalDate date;
    private LocalTime time;
    private PersonShortDTO planner;
    private List<InviteWithPersonDTO> invites;
}

