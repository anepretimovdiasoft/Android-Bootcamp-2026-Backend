package ru.examle.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);
    boolean existsByName(String name);
    List<Room> findByIsActive(boolean active);
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);
    Page<Room> findByIsActive(boolean active, Pageable pageable);
    Page<Room> findByCapacityGreaterThanEqual(Integer capacity, Pageable pageable);
}
