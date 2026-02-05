package com.teto.planner.dto;

import java.time.LocalDate;
import java.util.List;

public record BusySlotsResponse(LocalDate meetingDate, List<BusySlotDto> busySlots) {
}
