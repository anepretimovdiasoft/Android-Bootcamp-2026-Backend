package com.teto.planner.dto;

import java.util.UUID;

public record RoomDto(UUID id, String name, Integer capacity) {
}
