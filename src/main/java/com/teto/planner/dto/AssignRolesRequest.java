package com.teto.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record AssignRolesRequest(
        @NotEmpty List<String> roleSlugs
) {
}
