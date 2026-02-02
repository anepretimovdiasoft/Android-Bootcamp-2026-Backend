package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.Room;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomName(String roomName);
}