package ru.sicampus.bootcamp2026.repository;

import ru.sicampus.bootcamp2026.entity.meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface meetRepository extends JpaRepository<meet, Long> {
    List<meet> findByMeetDate(LocalDate date);
    List<meet> findByTitleContainingIgnoreCase(String keyword);
}