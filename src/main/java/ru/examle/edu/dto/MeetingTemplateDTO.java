package ru.examle.edu.dto;

import lombok.Data;
import ru.examle.edu.entity.enums.MeetingPriority;

import java.time.LocalDateTime;

@Data
public class MeetingTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private String titlePattern;
    private Integer defaultDurationHours;
    private MeetingPriority defaultPriority;
    private String requiredParticipants;
    private String suggestedRooms;
    private String agendaTemplate;
    private boolean isCompanyTemplate;
    private String department;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
