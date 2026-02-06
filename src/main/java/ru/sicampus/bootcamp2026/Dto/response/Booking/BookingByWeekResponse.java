package ru.sicampus.bootcamp2026.Dto.response.Booking;

import java.util.List;
import java.util.Map;

public class BookingByWeekResponse {
    private List<List<Map<String, Object>>> bookings;

    public void setBookings(List<List<Map<String, Object>>> bookings) {
        this.bookings = bookings;
    }
}
