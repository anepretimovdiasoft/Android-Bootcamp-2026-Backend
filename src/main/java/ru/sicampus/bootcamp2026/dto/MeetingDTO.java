package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
public class MeetingDTO {
    private long id;
    private long creatorId;
    private String title;
    private Date date;
    private Time startTime;
    private Time endTime;
    private List<Long> invitedUserIds;      //Ids of invited users
}
