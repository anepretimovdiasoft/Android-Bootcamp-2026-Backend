package ru.sicampus.bootcamp2026.service;

import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.FreeTimeResponse;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.MeetingStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
     * Получение встреч по статусу
     */
    List<MeetingResponse> getMeetingsByStatus(UUID userId, MeetingStatus status);

    /**
     * Поиск свободных временных слотов для встречи
     */
    List<FreeTimeResponse.FreeTimeSlot> findFreeTimeSlots(List<UUID> userIds, int durationMinutes);
}
