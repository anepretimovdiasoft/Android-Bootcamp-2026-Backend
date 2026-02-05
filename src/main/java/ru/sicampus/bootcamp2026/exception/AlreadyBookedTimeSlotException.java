package ru.sicampus.bootcamp2026.exception;

public class AlreadyBookedTimeSlotException extends RuntimeException {
    public AlreadyBookedTimeSlotException(String message) {
        super(message);
    }
}
