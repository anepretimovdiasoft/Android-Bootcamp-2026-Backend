package ru.examle.edu.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.examle.edu.entity.Meeting.MeetingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingDto {
    private Long id;
    private String title;
    private String description;
    private PersonSimpleDto organizer;
    private LocalDate meetingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private MeetingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<InvitationSimpleDto> invitations;
}

