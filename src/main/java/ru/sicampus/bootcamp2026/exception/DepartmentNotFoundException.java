package ru.sicampus.bootcamp2026.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super("Department not found!");
    }
}