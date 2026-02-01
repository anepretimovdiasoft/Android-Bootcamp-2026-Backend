package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.Users;


@Data
public class MeetingsDTO {

    private long id;
    private String topic;
    private String description;
    private Users creator;
    private String date;
    private String timeStart;
    private int duration;
    private String place;
    private String status;
}
