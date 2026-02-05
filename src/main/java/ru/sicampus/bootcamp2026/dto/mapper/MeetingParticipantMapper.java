package ru.sicampus.bootcamp2026.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.MeetingParticipant;

@Mapper(componentModel = "spring")
public interface MeetingParticipantMapper {
    // Преобразует MeetingParticipant в MeetingResponse.ParticipantInfo.
    @Mapping(source = "userId.id", target = "id")
    @Mapping(source = "userId.username", target = "username")
    MeetingResponse.ParticipantInfo toParticipantInfo(MeetingParticipant p);
}
