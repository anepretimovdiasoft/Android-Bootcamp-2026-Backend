package ru.sicampus.bootcamp2026.exception;

import org.springframework.http.HttpStatus;

public abstract class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}