package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class meetDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate meetDate;
    private LocalTime meetTime;
}