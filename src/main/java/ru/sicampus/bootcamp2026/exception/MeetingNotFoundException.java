package ru.sicampus.bootcamp2026.exception;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(Long id) {
        super("Meeting with id " + id + " not found");
    }
}
