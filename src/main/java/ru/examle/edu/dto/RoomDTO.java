package ru.examle.edu.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RoomDTO {
    private Long id;
    private String name;
    private String description;
    private Integer capacity;
    private String equipment;
    private boolean active = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
