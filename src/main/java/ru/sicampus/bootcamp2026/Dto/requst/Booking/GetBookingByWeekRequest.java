package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
@Data
public class GetBookingByWeekRequest {
    @Future(message = "the date cannot be in the past or at the moment")
    @JsonFormat(pattern = "yyyy:MM:dd")
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }
}
