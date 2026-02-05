package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.DateTime;

import java.time.LocalDate;
import java.util.Optional;

public interface DateRepository extends JpaRepository<DateTime, Long> {
    Optional<DateTime> findByDate(LocalDate date);
}
