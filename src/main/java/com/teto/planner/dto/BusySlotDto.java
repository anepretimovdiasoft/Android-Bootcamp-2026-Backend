package com.teto.planner.dto;

import java.util.UUID;

public record BusySlotDto(Integer hour, UUID meetingId) {
}
