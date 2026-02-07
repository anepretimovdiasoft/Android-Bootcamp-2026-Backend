package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBooingByDayRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBookingByWeekRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBookingUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByDayResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByMonthResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByWeekResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingUpdateResponse;

import java.time.LocalDateTime;

@Service
public interface BookingService {
    BookingByDayResponse getBookingByDay(GetBooingByDayRequest dto);
    BookingByWeekResponse getBookingByWeek(GetBookingByWeekRequest dt);
    void updateBooking(GetBookingUpdateRequest dto);

}
