package ru.example.edu.service;

import ru.example.edu.dto.InviteDTO;

public interface InviteService {
    InviteDTO updateInvite(Long id, InviteDTO dto);
}
