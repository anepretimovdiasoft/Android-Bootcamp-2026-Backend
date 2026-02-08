package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
public class GetBookingUpdateRequest {
    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime start_time;
    @Future
    @NotNull
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime end_time;
    private String name;
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private  LocalDateTime start;
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private  LocalDateTime end;
    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }
}
