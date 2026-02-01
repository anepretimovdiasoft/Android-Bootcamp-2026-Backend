package ru.sicampus.bootcamp2026.service;

import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.MeetingStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingService {

    /**
     * Создание новой встречи
     */
    MeetingResponse createMeeting(UUID organizerId, CreateMeetingRequest request);

    /**
     * Получение встречи по ID
     */
    MeetingResponse getMeetingById(UUID meetingId);

    /**
     * Получение всех встреч пользователя
     */
    List<MeetingResponse> getUserMeetings(UUID userId);

    /**
     * Отмена встречи
     */
    MeetingResponse cancelMeeting(UUID organizerId, UUID meetingId);

    /**
     * Удаление встречи
     */
    void deleteMeeting(UUID organizerId, UUID meetingId);

    /**
     * Получение встреч в определённом временном интервале
     */
    List<MeetingResponse> getMeetingsInRange(UUID userId, Instant start, Instant end);

    /**
     * Получение встреч по статусу
     */
    List<MeetingResponse> getMeetingsByStatus(UUID userId, MeetingStatus status);
}
