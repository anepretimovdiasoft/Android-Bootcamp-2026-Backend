package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.InvitationDTO;
import ru.examle.edu.entity.Invitation;
import ru.examle.edu.repository.InvitationRepository;
import ru.examle.edu.service.InvitationService;
import ru.examle.edu.ulti.InvitationMapper;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;

    @Override
    public Page<InvitationDTO> getAllInvitations(Pageable pageable) {
        return invitationRepository.findAll(pageable).map(invitationMapper::toDTO);
    }

    @Override
    public InvitationDTO getInvitationById(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        return invitationMapper.toDTO(invitation);
    }

    @Override
    @Transactional
    public InvitationDTO createInvitation(InvitationDTO invitationDTO) {
        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        Invitation savedInvitation = invitationRepository.save(invitation);
        return invitationMapper.toDTO(savedInvitation);
    }

    @Override
    @Transactional
    public InvitationDTO updateInvitation(Long id, InvitationDTO invitationDTO) {
        if (!invitationRepository.existsById(id)) {
            throw new RuntimeException("Invitation not found with id: " + id);
        }
        invitationDTO.setId(id);
        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        Invitation updatedInvitation = invitationRepository.save(invitation);
        return invitationMapper.toDTO(updatedInvitation);
    }

    @Override
    @Transactional
    public void deleteInvitation(Long id) {
        if (!invitationRepository.existsById(id)) {
            throw new RuntimeException("Invitation not found with id: " + id);
        }
        invitationRepository.deleteById(id);
    }
}
