package ru.sicampus.bootcamp2026.util;
import org.springframework.stereotype.Component;
import ru.sicampus.bootcamp2026.dto.MeetingInfoDTO;
import ru.sicampus.bootcamp2026.dto.ParticipantDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.MeetingParticipant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingMapper {
    public MeetingInfoDTO toInfoDTO(Meeting m, List<MeetingParticipant> participants) {
        MeetingInfoDTO dto = new MeetingInfoDTO();
        dto.setTopic(m.getTopic());
        dto.setDateTime(LocalDateTime.of(m.getCalendarDate(), m.getStartTime()));

        List<ParticipantDTO> pDtos = participants.stream().map(p -> {
            ParticipantDTO pd = new ParticipantDTO();
            pd.setFullName(p.getUser().getFullName());
            pd.setStatus(p.getInvitationStatus().getStatusName());
            return pd;
        }).collect(Collectors.toList());

        dto.setParticipants(pDtos);
        return dto;
    }
}