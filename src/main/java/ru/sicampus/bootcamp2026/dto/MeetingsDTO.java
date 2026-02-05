package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingsDTO {
    private long id;
    private String creatorName;
    private LocalDate date;

}