package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class MeetingValidator {
    public boolean validateStartEnd(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isAfter(LocalDateTime.now())
                && startTime.getYear() == endTime.getYear()
                && startTime.getMinute() == 0 && endTime.getMinute() == 0
                && startTime.getSecond() == 0 && endTime.getSecond() == 0
                && ChronoUnit.HOURS.between(startTime, endTime) == 1;
    }
}
