package ru.sicampus.bootcamp2026.util;

import ru.sicampus.bootcamp2026.exception.InvalidMeetingTimeException;

import java.time.LocalDateTime;

public class TimeValidator {

    public static boolean isFullHour(LocalDateTime time) {
        return time.getMinute() != 0 || time.getSecond() != 0 || time.getNano() != 0;
    }

    public static void validateMeetingTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (isFullHour(startTime)) {
            throw new InvalidMeetingTimeException("Start time must be on full hour");
        }
        if (isFullHour(endTime)) {
            throw new InvalidMeetingTimeException("End time must be on full hour");
        }
        if (!endTime.isAfter(startTime)) {
            throw new InvalidMeetingTimeException("End time must be after start time");
        }
    }
}