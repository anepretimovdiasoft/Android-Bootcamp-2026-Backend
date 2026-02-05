package ru.example.edu.dto;

import lombok.Data;

@Data
public class InviteWithMeetupDTO {
    private long id;
    private Boolean agree;
    private MeetupDTO meetup;
}
