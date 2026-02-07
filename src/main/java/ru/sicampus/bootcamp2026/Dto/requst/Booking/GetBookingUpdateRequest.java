package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
public class GetBookingUpdateRequest {
    @NotBlank
    @Future
    private LocalDateTime start_time;
    @NotBlank
    @Future
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm")
    private LocalDateTime end_time;
    @NotBlank
    private String name;
    @NotBlank
    @NotNull
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm")
    private  LocalDateTime start;
    @NotBlank
    @NotNull
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm")
    private  LocalDateTime end;
    @Positive
    @Min(value = 18)
    private  long age;
    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
