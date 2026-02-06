package ru.sicampus.bootcamp2026.Repository;

import ru.sicampus.bootcamp2026.Entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository {
    List<Booking> findByEmployee(String mail);
    List<Booking> findByStart(LocalDate date);
}
