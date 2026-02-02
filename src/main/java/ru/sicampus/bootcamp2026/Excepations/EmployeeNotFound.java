package ru.sicampus.bootcamp2026.Excepations;

public class EmployeeNotFound extends RuntimeException {
    public EmployeeNotFound(String message) {
        super(message);
    }
}
