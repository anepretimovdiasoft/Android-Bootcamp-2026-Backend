package ru.examle.edu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.examle.edu.entity.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    private String description;

    @NotNull(message = "Organizer ID is required")
    private Long organizerId;

    @NotNull(message = "Meeting date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate meetingDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    private Meeting.MeetingStatus status;
}
