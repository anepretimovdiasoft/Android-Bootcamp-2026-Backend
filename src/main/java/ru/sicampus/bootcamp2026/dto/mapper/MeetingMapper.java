package ru.sicampus.bootcamp2026.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.Meeting;

@Mapper(componentModel = "spring", uses = {MeetingParticipantMapper.class})
public interface MeetingMapper {

    // Преобразование сущности встречи в DTO (organizer -> OrganizerInfo, participants -> ParticipantInfo)
    @Mapping(source = "organizer_id.id", target = "organizer.id")
    @Mapping(source = "organizer_id.username", target = "organizer.username")
    @Mapping(source = "organizer_id.email", target = "organizer.email")
    MeetingResponse toMeetingResponse(Meeting meeting);

    // Создание Meeting из запроса (связи и timestamps ставит сервис)
    Meeting fromCreateRequest(CreateMeetingRequest req);
}
