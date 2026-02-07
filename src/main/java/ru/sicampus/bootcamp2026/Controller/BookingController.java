package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByDayResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByMonthResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByWeekResponse;
import ru.sicampus.bootcamp2026.Service.BookingService;

@RestController
@RequestMapping("/api/Booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @GetMapping("/BookingsByDay")
    public ResponseEntity<?> getBookingByDay(){
            BookingByDayResponse bookingByDayResponse=bookingService.getBookingByDay();
            return ResponseEntity.ok(bookingByDayResponse);
    }
    @GetMapping("/BookingByMonth")
    public  ResponseEntity<?> getBookingByMonth(){
            BookingByMonthResponse bookingByMonthResponse= bookingService.getBookingByMonth();
            return ResponseEntity.ok(bookingByMonthResponse);
    }
    @GetMapping("/BookingByWeek")
    public  ResponseEntity<?> getBookingByWeek(){
            BookingByWeekResponse bookingByWeekResponse= bookingService.getBookingByWeek();
            return ResponseEntity.ok(bookingByWeekResponse);
    }

}
