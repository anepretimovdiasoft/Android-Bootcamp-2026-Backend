package ru.sicampus.bootcamp2026.util;

import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;

public final class MeetingMapper {

    private MeetingMapper() {}

    public static MeetingResponse toResponse(Meeting m) {
        return new MeetingResponse(m.getId(), m.getTitle(), m.getStartsAt(), m.getEndsAt());
    }
}