package ru.sicampus.bootcamp2026.services;

import ru.sicampus.bootcamp2026.dtos.InvitationDto;

import java.util.List;

public interface InvitationService {
    InvitationDto createInvitation(InvitationDto dto);
    InvitationDto getInvitationById(Long id);
    List<InvitationDto> getInvitationsByUserId(Long userId);
    List<InvitationDto> getInvitationsByMeetingId(Long meetingId);
    InvitationDto updateInvitationStatus(Long id, String status);
    void deleteInvitation(Long id);
}
