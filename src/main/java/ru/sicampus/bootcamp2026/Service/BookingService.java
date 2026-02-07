package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByDayResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByMonthResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByWeekResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingUpdateResponse;

import java.time.LocalDateTime;

@Service
public interface BookingService {
    BookingByDayResponse getBookingByDay();
    BookingByMonthResponse getBookingByMonth();
    BookingByWeekResponse getBookingByWeek();

}
