package ru.sicampus.bootcamp2026.exception.meeting;

public class TimeIsOnHourException extends UserBusyInThisTimeException {
    public TimeIsOnHourException(String message) {
        super(message);
    }
}
