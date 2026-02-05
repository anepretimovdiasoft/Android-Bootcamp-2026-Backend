package ru.examle.edu.service;

import ru.examle.edu.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();
    RoomDTO getRoomById(Long id);
    RoomDTO getRoomByName(String name);
    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO updateRoom(Long id, RoomDTO roomDTO);
    void deleteRoom(Long id);
    List<RoomDTO> getActiveRooms();
    List<RoomDTO> getRoomsByMinCapacity(Integer minCapacity);
}
