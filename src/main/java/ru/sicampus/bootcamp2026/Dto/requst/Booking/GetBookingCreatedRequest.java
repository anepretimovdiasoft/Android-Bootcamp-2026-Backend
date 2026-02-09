package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class GetBookingCreatedRequest {
    @Future(message = "the date cannot be in the past or at the moment")
    @NotNull(message = "The date cannot be empty")
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime end_time;
    @NotNull(message = "The date cannot be empty")
    @Future(message = "the date cannot be in the past or at the moment")
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime start_time;
    @NotNull(message = "The password cannot be empty.")
    @NotBlank(message = "the password cannot be an empty string or contain spaces")
    private  String name;

    public LocalDateTime getStart() {
        return start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public String getName() {
        return name;
    }
}
