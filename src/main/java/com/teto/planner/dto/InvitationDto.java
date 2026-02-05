package com.teto.planner.dto;

import com.teto.planner.entity.ParticipantRole;
import com.teto.planner.entity.ParticipantStatus;

public record InvitationDto(MeetingDto meeting, ParticipantRole myRole, ParticipantStatus myStatus) {
}
