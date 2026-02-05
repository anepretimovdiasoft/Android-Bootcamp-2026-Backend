package com.teto.planner.dto;

import java.util.List;

public record UsersPage(List<UserSummaryDto> items, PageMeta meta) {
}
