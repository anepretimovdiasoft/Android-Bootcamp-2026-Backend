package com.teto.planner.dto;

import java.util.List;

public record RoomsPage(List<RoomDto> items, PageMeta meta) {
}
