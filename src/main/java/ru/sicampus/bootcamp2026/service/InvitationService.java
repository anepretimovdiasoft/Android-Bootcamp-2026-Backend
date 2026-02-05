package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDto;

import java.util.List;

public interface InvitationService {
    List<InvitationDto> getAllInvitation();

    InvitationDto getInvitationById(Long id);

    InvitationDto createInvitationD(InvitationDto dto);

    InvitationDto updateInvitation(Long id, InvitationDto dto);

    void deleteInvitation(Long id);
}
