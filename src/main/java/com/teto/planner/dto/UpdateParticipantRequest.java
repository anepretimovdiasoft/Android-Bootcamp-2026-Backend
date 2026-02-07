package com.teto.planner.dto;

import com.teto.planner.entity.ParticipantStatus;

public record UpdateParticipantRequest(ParticipantStatus status) {
}
