package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.RoomDTO;

public interface RoomService {
    Page<RoomDTO> getAllRooms(Pageable pageable);
    RoomDTO getRoomById(Long id);
    RoomDTO getRoomByName(String name);
    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO updateRoom(Long id, RoomDTO roomDTO);
    void deleteRoom(Long id);
    Page<RoomDTO> getActiveRooms(Pageable pageable);
    Page<RoomDTO> getRoomsByMinCapacity(Integer minCapacity, Pageable pageable);
}
