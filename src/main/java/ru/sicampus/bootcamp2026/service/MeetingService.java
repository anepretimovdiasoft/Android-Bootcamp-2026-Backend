package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingDtos.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingParticipantResponse;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.UpdateMeetingRequest;

import java.util.List;

public interface MeetingService {

    List<MeetingResponse> list();

    MeetingResponse get(long id);

    List<MeetingParticipantResponse> getParticipants(long meetingId);

    MeetingResponse create(CreateMeetingRequest req);

    MeetingResponse update(long id, UpdateMeetingRequest req);

    void delete(long id);
}