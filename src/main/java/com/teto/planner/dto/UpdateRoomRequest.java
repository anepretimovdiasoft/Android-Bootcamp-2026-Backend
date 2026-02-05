package com.teto.planner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateRoomRequest(
        @Size(max = 128) String name,
        @Min(1) Integer capacity
) {
}
