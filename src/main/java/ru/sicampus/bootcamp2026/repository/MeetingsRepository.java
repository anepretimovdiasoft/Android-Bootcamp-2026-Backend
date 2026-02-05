package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meetings;
import ru.sicampus.bootcamp2026.entity.Users;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MeetingsRepository extends JpaRepository<Meetings, Long> {
    Optional<Meetings> findByCreatorIdAndDate(Users id, LocalDate date);
}