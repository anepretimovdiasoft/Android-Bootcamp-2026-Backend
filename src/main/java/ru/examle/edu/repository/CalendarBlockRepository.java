package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.CalendarBlock;

public interface CalendarBlockRepository extends JpaRepository<CalendarBlock, Long> {
}
