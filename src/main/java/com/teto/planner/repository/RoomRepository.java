package com.teto.planner.repository;

import com.teto.planner.entity.RoomEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
}
