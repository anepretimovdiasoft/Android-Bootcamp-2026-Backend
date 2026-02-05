package ru.sicampus.bootcamp2026.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.enums.MeetingsStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingsDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private MeetingsStatus status;
    private UserDto creator; // кто создал встречу
    private List<MeetingAttendeeDto> attendees; // участники
}
