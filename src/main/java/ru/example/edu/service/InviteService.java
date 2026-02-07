package ru.example.edu.service;

import ru.example.edu.dto.InviteDTO;
import ru.example.edu.dto.InviteUpdateDTO;

public interface InviteService {
    InviteDTO updateInvite(Long id, InviteUpdateDTO dto);
}
