package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBooingByDayRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBookingByWeekRequest;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByDayResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByMonthResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByWeekResponse;
import ru.sicampus.bootcamp2026.Service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/Booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/BookingByDay")
    public ResponseEntity<?> getBookingByDay(
            @Valid @RequestBody GetBooingByDayRequest dto
    ){
        return ResponseEntity.ok(
                bookingService.getBookingByDay(dto)
        );
    }
    @PostMapping("/BookingByWeek")
    public  ResponseEntity<?> getBookingByWeek(@Valid @RequestBody GetBookingByWeekRequest dto){
            BookingByWeekResponse bookingByWeekResponse= bookingService.getBookingByWeek(dto);
            return ResponseEntity.ok(bookingByWeekResponse);
    }

}
