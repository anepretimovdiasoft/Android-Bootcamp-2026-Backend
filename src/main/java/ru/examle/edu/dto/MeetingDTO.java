package ru.examle.edu.dto;

import lombok.Data;
import ru.examle.edu.entity.enums.MeetingPriority;
import ru.examle.edu.entity.enums.MeetingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MeetingDTO {
    private Long id;
    private String title;
    private String description;
    private Long organizerId;
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String recurrencePattern;
    private LocalDate recurrenceEndDate;
    private MeetingPriority meetingPriority;
    private MeetingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
