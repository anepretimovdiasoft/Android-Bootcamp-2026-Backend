package com.teto.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

public record AddParticipantsRequest(
        @NotEmpty List<UUID> userIds
) {
}
