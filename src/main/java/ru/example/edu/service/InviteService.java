package ru.example.edu.service;

import ru.example.edu.dto.InviteCreateDTO;
import ru.example.edu.dto.InviteDTO;
import ru.example.edu.dto.InviteUpdateDTO;
import ru.example.edu.dto.InviteWithMeetupDTO;

import java.util.List;

public interface InviteService {
    InviteDTO updateInvite(Long id, InviteUpdateDTO dto);

    List<InviteWithMeetupDTO> getInvitesByParticipantId(Long id);

    InviteDTO createInvite(InviteCreateDTO dto);
}
