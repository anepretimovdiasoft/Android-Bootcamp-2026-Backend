package ru.sicampus.bootcamp2026.Excepations;

public class BookingNotFound extends RuntimeException {
    public BookingNotFound(String message) {
        super(message);
    }
}
