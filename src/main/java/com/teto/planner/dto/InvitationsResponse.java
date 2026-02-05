package com.teto.planner.dto;

import java.util.List;

public record InvitationsResponse(List<InvitationDto> items, PageMeta meta) {
}
