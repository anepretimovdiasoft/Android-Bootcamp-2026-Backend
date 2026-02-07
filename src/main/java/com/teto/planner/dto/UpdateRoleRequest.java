package com.teto.planner.dto;

import jakarta.validation.constraints.Size;

public record UpdateRoleRequest(
        @Size(max = 32) String slug,
        @Size(max = 64) String name
) {
}
