package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponse;
import ru.sicampus.bootcamp2026.model.MeetingStatus;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    /*
        TODO: Внедрить репозиторий позже
    */

    @Override
    public MeetingResponse createMeeting(UUID organizerId, CreateMeetingRequest request) {
        /*
            TODO: Реализовать логику создания встречи
        */
        throw new UnsupportedOperationException("Метод createMeeting еще не реализован");
    }

    @Override
    public MeetingResponse getMeetingById(UUID meetingId) {
        /*
            TODO: Реализовать получение встречи по ID
        */
        throw new UnsupportedOperationException("Метод getMeetingById еще не реализован");
    }

    @Override
    public List<MeetingResponse> getUserMeetings(UUID userId) {
        /*
            TODO: Реализовать получение всех встреч пользователя
        */
        throw new UnsupportedOperationException("Метод getUserMeetings еще не реализован");
    }

    @Override
    public MeetingResponse cancelMeeting(UUID organizerId, UUID meetingId) {
        /*
            TODO: Реализовать отмену встречи
        */
        throw new UnsupportedOperationException("Метод cancelMeeting еще не реализован");
    }

    @Override
    public void deleteMeeting(UUID organizerId, UUID meetingId) {
        /*
            TODO: Реализовать удаление встречи
        */
        throw new UnsupportedOperationException("Метод deleteMeeting еще не реализован");
    }

    @Override
    public List<MeetingResponse> getMeetingsInRange(UUID userId, Instant start, Instant end) {
        /*
            TODO: Реализовать поиск встреч в диапазоне времени
        */
        throw new UnsupportedOperationException("Метод getMeetingsInRange еще не реализован");
    }

    @Override
    public List<MeetingResponse> getMeetingsByStatus(UUID userId, MeetingStatus status) {
        /*
            TODO: Реализовать поиск встреч по статусу
        */
        throw new UnsupportedOperationException("Метод getMeetingsByStatus еще не реализован");
    }
}