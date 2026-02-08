package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class GetBookingCreatedRequest {
    @Future
    @NotNull
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime end_time;
    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime start_time;
    @NotNull
    @NotBlank
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
