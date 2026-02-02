package ru.sicampus.bootcamp2026.dto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingInfoDTO {
    private String topic;
    private LocalDateTime dateTime;
    private List<ParticipantDTO> participants;
}