package ru.sicampus.bootcamp2026.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dtos.InvitationDto;
import ru.sicampus.bootcamp2026.entities.Invitation;
import ru.sicampus.bootcamp2026.entities.Meeting;
import ru.sicampus.bootcamp2026.entities.User;
import ru.sicampus.bootcamp2026.exeptions.InvitationNotFound;
import ru.sicampus.bootcamp2026.exeptions.MeetingNotFound;
import ru.sicampus.bootcamp2026.exeptions.UserNotFound;
import ru.sicampus.bootcamp2026.repositories.InvitationRepository;
import ru.sicampus.bootcamp2026.repositories.MeetingRepository;
import ru.sicampus.bootcamp2026.repositories.UserRepository;
import ru.sicampus.bootcamp2026.services.InvitationService;
import ru.sicampus.bootcamp2026.utils.InvitationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    @Override
    @Transactional
    public InvitationDto createInvitation(InvitationDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFound("Пользователь с id " + dto.getUserId() + " не найден"));

        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new MeetingNotFound("Встреча с id " + dto.getMeetingId() + " не найдена"));

        Invitation invitation = new Invitation();
        invitation.setStatus(dto.getStatus() != null ? dto.getStatus() : "Ожидается");
        invitation.setUser(user);
        invitation.setMeeting(meeting);

        Invitation savedInvitation = invitationRepository.save(invitation);
        return InvitationMapper.convertToDto(savedInvitation);
    }

    @Override
    public InvitationDto getInvitationById(Long id) {
        return invitationRepository.findById(id)
                .map(InvitationMapper::convertToDto)
                .orElseThrow(() -> new InvitationNotFound("Приглашение с id " + id + " не найдено"));
    }

    @Override
    public List<InvitationDto> getInvitationsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound("Пользователь с id " + userId + " не найден");
        }
        return invitationRepository.findAllByUserId(userId).stream()
                .map(InvitationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvitationDto> getInvitationsByMeetingId(Long meetingId) {
        if (!meetingRepository.existsById(meetingId)) {
            throw new MeetingNotFound("Встреча с id " + meetingId + " не найдена");
        }
        return invitationRepository.findAll().stream()
                .filter(invitation -> invitation.getMeeting().getId().equals(meetingId))
                .map(InvitationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvitationDto updateInvitationStatus(Long id, String status) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new InvitationNotFound("Приглашение с id " + id + " не найдено"));

        // Валидация статуса
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Некорректный статус: " + status +
                    ". Доступные статусы: Принято, Отклонено, Ожидается");
        }

        invitation.setStatus(status);
        Invitation updatedInvitation = invitationRepository.save(invitation);
        return InvitationMapper.convertToDto(updatedInvitation);
    }

    @Override
    @Transactional
    public void deleteInvitation(Long id) {
        if (!invitationRepository.existsById(id)) {
            throw new InvitationNotFound("Приглашение с id " + id + " не найдено");
        }
        invitationRepository.deleteById(id);
    }

    private boolean isValidStatus(String status) {
        return status.equals("Принято") ||
                status.equals("Отклонено") ||
                status.equals("Ожидается");
    }
}