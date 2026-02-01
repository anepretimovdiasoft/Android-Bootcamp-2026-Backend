package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.InviteDTO;
import ru.example.edu.entity.Invite;
import ru.example.edu.exception.InviteNotFoundException;
import ru.example.edu.repository.InviteRepository;
import ru.example.edu.service.InviteService;
import ru.example.edu.util.InviteMapper;


@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;

    @Override
    public InviteDTO updateInvite(Long id, InviteDTO dto) {
        Invite invite = inviteRepository.findById(id).orElseThrow(() -> new InviteNotFoundException("Invite not found!"));

        invite.setAgree(dto.getAgree());
        return InviteMapper.convertToDto(inviteRepository.save(invite));
    }
}
