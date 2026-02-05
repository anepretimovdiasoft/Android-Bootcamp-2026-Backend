package ru.examle.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.examle.edu.entity.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingSimpleDto {
    private Long id;
    private String title;
    private LocalDate meetingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Meeting.MeetingStatus status;
}
