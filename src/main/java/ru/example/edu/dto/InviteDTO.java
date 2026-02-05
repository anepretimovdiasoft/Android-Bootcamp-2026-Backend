package ru.example.edu.dto;

import lombok.Data;

@Data
public class InviteDTO {
    private long id;
    private Boolean agree;
    private MeetupDTO meetup;
    private PersonShortDTO participant;
}