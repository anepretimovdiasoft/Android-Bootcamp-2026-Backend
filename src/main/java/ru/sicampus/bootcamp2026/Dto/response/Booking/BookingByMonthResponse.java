package ru.sicampus.bootcamp2026.Dto.response.Booking;

import ru.sicampus.bootcamp2026.Entity.Booking;

import java.util.List;
import java.util.Map;

public class BookingByMonthResponse {
    private List<Map<String,Object>> bookingList;

    public void setBookings(List<Map<String,Object>> bookings) {
        this.bookingList = bookings;
    }
}
