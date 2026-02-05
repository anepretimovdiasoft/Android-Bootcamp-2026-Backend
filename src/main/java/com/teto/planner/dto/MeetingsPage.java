package com.teto.planner.dto;

import java.util.List;

public record MeetingsPage(List<MeetingDto> items, PageMeta meta) {
}
