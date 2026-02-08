package ru.sicampus.bootcamp2026.Dto.requst.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class GetBooingByDayRequest{
    @Future(message = "the date cannot be in the past or at the moment")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy:MM:dd")
    private LocalDate  date;

    public LocalDate getDate() {
        return date;
    }
}
