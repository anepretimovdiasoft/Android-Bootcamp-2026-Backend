package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.FreeTimeResponse;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.MeetingStatus;

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
     * Получение встреч пользователя с пагинацией.
     */
    Page<MeetingResponse> getUserMeetings(UUID userId, Pageable pageable);

    /**
     * Отмена встречи
     */
    MeetingResponse cancelMeeting(UUID organizerId, UUID meetingId);

    /**
     * Удаление встречи
     */
    void deleteMeeting(UUID organizerId, UUID meetingId);

    /**
     * Получение встреч пользователя по статусу с пагинацией.
     */
    Page<MeetingResponse> getMeetingsByStatus(UUID userId, MeetingStatus status, Pageable pageable);

    /**
     * Поиск свободных временных слотов для встречи
     */
    List<FreeTimeResponse.FreeTimeSlot> findFreeTimeSlots(List<UUID> userIds, int durationMinutes);
}
