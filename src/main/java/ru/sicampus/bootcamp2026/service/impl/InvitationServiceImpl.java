package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.exception.WrongDateFormatException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public InvitationDTO createInvitation(InvitationDTO dto) {
        Invitation invitation = new Invitation();

        invitation.setInvitedUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")));
        invitation.setMeeting(meetingRepository.findById(dto.getMeetingId()).orElseThrow(() -> new MeetingNotFoundException("Meeting not found")));
        invitation.setAccepted(false);

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public InvitationDTO getInvitationById(Long id) {
        return invitationRepository.findById(id)
                .map(InvitationMapper::convertToDto)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));
    }

    @Override
    public List<InvitationDTO> getAllInvitations() {
        return invitationRepository.findAll().stream()
                .map(InvitationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<InvitationDTO> getAllInvitationsPaginated(Pageable pageable) {
        return invitationRepository.findAll(pageable).map(InvitationMapper::convertToDto);
    }

    @Override
    public Page<InvitationDTO> getAllUnacceptedInvitationsByUserIdPaginated(Long id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<InvitationDTO> unacceptedInvites = new ArrayList<>();
        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user);
        invites.forEach(invite -> {
            if (!invite.isAccepted()) {
                unacceptedInvites.add(InvitationMapper.convertToDto(invite));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), unacceptedInvites.size());

        List<InvitationDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = unacceptedInvites.subList(start, end);
        }

        return new PageImpl<>(pageContent, pageable, unacceptedInvites.size());
    }

    @Override
    public List<InvitationDTO> getAllInvitationsByUserId(Long id, String since) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        //LocalDate filterDate = LocalDate.ofInstant(since.toInstant(), ZoneId.of("UTC"));
        LocalDate filterDate;
        try {
            filterDate = LocalDate.parse(since);
        } catch (Exception e) {
            throw new WrongDateFormatException("Wrong dateString format");
        }

        return invitationRepository.findAllByInvitedUser(user).stream()
                .filter(invitation -> LocalDate.ofInstant(invitation.getMeeting().getDate().toInstant(), ZoneId.of("UTC")).compareTo(filterDate) >= 0)
                .map(InvitationMapper::convertToDto)
                .toList();
    }

    @Override
    public Page<InvitationDTO> getAllInvitationsByUserIdPaginated(Long id, String since, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        //LocalDate filterDate = LocalDate.ofInstant(since.toInstant(), ZoneId.of("UTC"));

        LocalDate filterDate;
        try {
            filterDate = LocalDate.parse(since);
        } catch (Exception e) {
            throw new WrongDateFormatException("Wrong dateString format");
        }

        List<InvitationDTO> invitations = new ArrayList<>();
        List<Invitation> allInvites = invitationRepository.findAllByInvitedUser(user);
        allInvites.forEach(invite -> {
            LocalDate localDate = LocalDate.ofInstant(invite.getMeeting().getDate().toInstant(), ZoneId.of("UTC"));
            if (localDate.compareTo(filterDate) >= 0) {
                invitations.add(InvitationMapper.convertToDto(invite));
            }
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), invitations.size());

        List<InvitationDTO> pageContent;
        if (start >= end) {
            pageContent = new ArrayList<>();
        } else {
            pageContent = invitations.subList(start, end);
        }

        return new PageImpl<>(pageContent, pageable, invitations.size());
    }

    @Override
    public InvitationDTO updateInvitation(Long id, InvitationDTO dto) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));

        invitation.setInvitedUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")));
        invitation.setMeeting(meetingRepository.findById(dto.getMeetingId()).orElseThrow(() -> new MeetingNotFoundException("Meeting not found")));
        invitation.setAccepted(dto.isAccepted());

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public InvitationDTO confirmInvitationById(Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));

        invitation.setAccepted(true);

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }
}
