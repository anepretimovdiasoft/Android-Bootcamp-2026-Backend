package com.teto.planner.dto;

import java.util.List;

public record RolesPage(List<RoleDto> items, PageMeta meta) {
}
