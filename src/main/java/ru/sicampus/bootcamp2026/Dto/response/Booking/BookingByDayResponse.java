package ru.sicampus.bootcamp2026.Dto.response.Booking;

import ru.sicampus.bootcamp2026.Entity.Booking;

import java.util.List;

public class BookingByDayResponse {
    private List<Booking> bookings;

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
