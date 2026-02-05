package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.RoomDTO;
import ru.examle.edu.entity.Room;

@Component
public class RoomMapper {
    public RoomDTO toDTO(Room room) {
        if (room == null) return null;
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setDescription(room.getDescription());
        dto.setCapacity(room.getCapacity());
        dto.setEquipment(room.getEquipment());
        dto.setActive(room.isActive());
        dto.setCreatedAt(room.getCreatedAt());
        dto.setUpdatedAt(room.getUpdatedAt());
        return dto;
    }

    public Room toEntity(RoomDTO dto){
        if (dto == null) return null;
        Room room = new Room();
        if (dto.getId() != null) room.setId(dto.getId());
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());
        if (dto.getCapacity() != null) room.setCapacity(dto.getCapacity());
        room.setEquipment(dto.getEquipment());
        room.setActive(dto.isActive());
        return room;
    }
}
