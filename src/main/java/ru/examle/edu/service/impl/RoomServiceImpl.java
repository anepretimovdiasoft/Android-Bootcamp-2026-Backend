package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.RoomDTO;


import ru.examle.edu.entity.Room;
import ru.examle.edu.repository.RoomRepository;
import ru.examle.edu.service.RoomService;
import ru.examle.edu.ulti.RoomMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        return roomMapper.toDTO(room);
    }

    @Override
    public RoomDTO getRoomByName(String name) {
        Room room = roomRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Room not found with name: " + name));
        return roomMapper.toDTO(room);
    }

    @Override
    @Transactional
    public RoomDTO createRoom(RoomDTO roomDTO) {
        if (roomRepository.existsByName(roomDTO.getName())) {
            throw new RuntimeException("Room with name '" + roomDTO.getName() + "' already exists");
        }

        Room room = roomMapper.toEntity(roomDTO);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDTO(savedRoom);
    }

    @Override
    @Transactional
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));


        if (roomDTO.getName() != null &&
                !roomDTO.getName().equals(existingRoom.getName()) &&
                roomRepository.existsByName(roomDTO.getName())) {
            throw new RuntimeException("Room with name '" + roomDTO.getName() + "' already exists");
        }


        if (roomDTO.getName() != null) existingRoom.setName(roomDTO.getName());
        if (roomDTO.getDescription() != null) existingRoom.setDescription(roomDTO.getDescription());
        if (roomDTO.getCapacity() != null) existingRoom.setCapacity(Integer.valueOf(roomDTO.getCapacity()));
        if (roomDTO.getEquipment() != null) existingRoom.setEquipment(String.valueOf(roomDTO.getEquipment()));

        Room updatedRoom = roomRepository.save(existingRoom);
        return roomMapper.toDTO(updatedRoom);
    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDTO> getActiveRooms() {
        return roomRepository.findByIsActive(true).stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomsByMinCapacity(Integer minCapacity) {
        return roomRepository.findByCapacityGreaterThanEqual(minCapacity).stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }
}