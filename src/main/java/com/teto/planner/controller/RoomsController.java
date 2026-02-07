package com.teto.planner.controller;

import com.teto.planner.dto.CreateRoomRequest;
import com.teto.planner.dto.RoomDto;
import com.teto.planner.dto.RoomsPage;
import com.teto.planner.dto.UpdateRoomRequest;
import com.teto.planner.service.RoomService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {
    private final RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public RoomsPage listRooms(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "50") int size
    ) {
        return roomService.listRooms(page, size);
    }

    @PostMapping
    public RoomDto createRoom(@Valid @RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request.name(), request.capacity());
    }

    @GetMapping("/{roomId}")
    public RoomDto getRoom(@PathVariable UUID roomId) {
        return roomService.getRoom(roomId);
    }

    @PatchMapping("/{roomId}")
    public RoomDto patchRoom(@PathVariable UUID roomId, @Valid @RequestBody UpdateRoomRequest request) {
        return roomService.updateRoom(roomId, request.name(), request.capacity());
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public RoomsPage listAvailable(
            @RequestParam(value = "meetingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate meetingDate,
            @RequestParam(value = "startHour") Integer startHour,
            @RequestParam(value = "capacity", required = false) Integer capacity,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "50") int size
    ) {
        return roomService.listAvailable(meetingDate, startHour, capacity, page, size);
    }
}
