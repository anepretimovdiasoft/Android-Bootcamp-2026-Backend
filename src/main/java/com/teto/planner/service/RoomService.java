package com.teto.planner.service;

import com.teto.planner.dto.PageMeta;
import com.teto.planner.dto.RoomDto;
import com.teto.planner.dto.RoomsPage;
import com.teto.planner.entity.RoomEntity;
import com.teto.planner.exception.NotFoundException;
import com.teto.planner.mapper.RoomMapper;
import com.teto.planner.pagination.Pagination;
import com.teto.planner.repository.RoomRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Transactional(readOnly = true)
    public RoomsPage listRooms(int page, int size) {
        Page<RoomEntity> rooms = roomRepository.findAll(Pagination.pageRequest(
                page,
                size,
                Sort.by(Sort.Order.asc("name"), Sort.Order.asc("id"))
        ));
        List<RoomDto> items = rooms.getContent().stream().map(roomMapper::toDto).collect(Collectors.toList());
        return new RoomsPage(items, new PageMeta(page, size, rooms.getTotalElements()));
    }

    @Transactional(readOnly = true)
    public RoomsPage listAvailable(LocalDate meetingDate, Integer startHour, Integer capacity, int page, int size) {
        Short hour = startHour != null ? startHour.shortValue() : null;
        Page<RoomEntity> rooms = roomRepository.findAvailable(
                meetingDate,
                hour,
                capacity,
                Pagination.pageRequest(page, size, Sort.by(Sort.Order.asc("name"), Sort.Order.asc("id")))
        );
        List<RoomDto> items = rooms.getContent().stream().map(roomMapper::toDto).collect(Collectors.toList());
        return new RoomsPage(items, new PageMeta(page, size, rooms.getTotalElements()));
    }

    @Transactional(readOnly = true)
    public RoomDto getRoom(UUID roomId) {
        return roomMapper.toDto(findRoom(roomId));
    }

    @Transactional
    public RoomDto createRoom(String name, Integer capacity) {
        RoomEntity room = new RoomEntity();
        room.setId(UUID.randomUUID());
        room.setName(name);
        room.setCapacity(capacity);
        return roomMapper.toDto(roomRepository.save(room));
    }

    @Transactional
    public RoomDto updateRoom(UUID roomId, String name, Integer capacity) {
        RoomEntity room = findRoom(roomId);
        if (name != null) {
            room.setName(name);
        }
        if (capacity != null) {
            room.setCapacity(capacity);
        }
        return roomMapper.toDto(room);
    }

    @Transactional
    public void deleteRoom(UUID roomId) {
        RoomEntity room = findRoom(roomId);
        roomRepository.delete(room);
    }

    @Transactional(readOnly = true)
    public RoomEntity findRoom(UUID roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("ROOM_NOT_FOUND", "Room not found"));
    }
}
