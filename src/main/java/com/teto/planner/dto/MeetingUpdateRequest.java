package com.teto.planner.dto;

import com.teto.planner.entity.MeetingStatus;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record MeetingUpdateRequest(
        @Size(max = 256) String title,
        String description,
        UUID roomId,
        MeetingStatus status
) {
}
