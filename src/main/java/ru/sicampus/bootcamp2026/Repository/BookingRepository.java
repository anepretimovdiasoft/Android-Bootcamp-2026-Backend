package ru.sicampus.bootcamp2026.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Employee;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByEmployee(Employee employee);
    List<Booking> findByStart(LocalDate date);
    Page<Booking> findByEmployee(Employee employee, Pageable pageable);
    Booking findByName(String name);
}
