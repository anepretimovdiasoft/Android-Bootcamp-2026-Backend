package ru.example.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.edu.entity.Meetup;

import java.util.List;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, Long> {
    List<Meetup> findByPlannerId(Long id);
}
