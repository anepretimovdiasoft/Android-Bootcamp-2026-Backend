package ru.sicampus.bootcamp2026.Excepations;

public class EmployeeFound extends RuntimeException {
    public EmployeeFound(String message) {
        super(message);
    }
}
