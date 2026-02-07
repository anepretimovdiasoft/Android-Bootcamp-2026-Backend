package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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


}
