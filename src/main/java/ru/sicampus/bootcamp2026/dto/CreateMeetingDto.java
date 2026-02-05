package ru.sicampus.bootcamp2026.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.enums.MeetingsStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMeetingDto {
    private Long creatorId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private MeetingsStatus status;

    private List<Long> attendeeIds;
}
