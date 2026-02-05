package com.teto.planner.mapper;

import com.teto.planner.dto.RoomDto;
import com.teto.planner.entity.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomDto toDto(RoomEntity room) {
        return new RoomDto(room.getId(), room.getName(), room.getCapacity());
    }
}
