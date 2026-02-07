package ru.sicampus.bootcamp2026.Dto.response.Booking;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@NoArgsConstructor
public class BookingByWeekResponse {
    private Map<String, List<Map<String, Object>>> bookings;

    public BookingByWeekResponse(Map<String, List<Map<String, Object>>> bookings) {
        this.bookings = bookings;
    }

    public Map<String, List<Map<String, Object>>> getBookings() {
        return bookings;
    }
}
