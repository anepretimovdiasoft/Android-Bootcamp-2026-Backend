package ru.examle.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.*;
import ru.examle.edu.entity.Invitation;
import ru.examle.edu.entity.Meeting;
import ru.examle.edu.entity.Person;
import ru.examle.edu.entity.Invitation.InvitationStatus;
import ru.examle.edu.exception.ResourceNotFoundException;
import ru.examle.edu.exception.ValidationException;
import ru.examle.edu.repository.InvitationRepository;
import ru.examle.edu.repository.MeetingRepository;
import ru.examle.edu.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    public List<InvitationDto> getAllInvitations() {
        return invitationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public InvitationDto getInvitationById(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + id));
        return convertToDto(invitation);
    }

    @Transactional
    public InvitationDto createInvitation(InvitationRequest request) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + request.getMeetingId()));

        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + request.getPersonId()));

        if (meeting.getOrganizer().getId().equals(person.getId())) {
            throw new ValidationException("Organizer cannot be invited to their own meeting");
        }

        invitationRepository.findByMeetingIdAndPersonId(meeting.getId(), person.getId())
                .ifPresent(inv -> {
                    throw new ValidationException("Invitation already exists for this meeting and person");
                });

        Invitation invitation = new Invitation();
        invitation.setMeeting(meeting);
        invitation.setPerson(person);
        invitation.setStatus(request.getStatus() != null ? request.getStatus() : InvitationStatus.PENDING);
        invitation.setCreatedAt(LocalDateTime.now());
        invitation.setUpdatedAt(LocalDateTime.now());

        invitation = invitationRepository.save(invitation);
        return convertToDto(invitation);
    }

    @Transactional
    public InvitationDto updateInvitationStatus(Long id, InvitationUpdateRequest request) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + id));

        if (invitation.getStatus() == InvitationStatus.CANCELLED) {
            throw new ValidationException("Cannot update a cancelled invitation");
        }

        if (request.getStatus() == InvitationStatus.ACCEPTED ||
                request.getStatus() == InvitationStatus.DECLINED) {
            invitation.setResponseDate(LocalDateTime.now());
        }

        invitation.setStatus(request.getStatus());
        invitation.setUpdatedAt(LocalDateTime.now());
        invitation = invitationRepository.save(invitation);
        return convertToDto(invitation);
    }

    @Transactional
    public void deleteInvitation(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + id));

        invitationRepository.delete(invitation);
    }

    public List<InvitationDto> getInvitationsByPerson(Long personId) {
        return invitationRepository.findByPersonId(personId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<InvitationDto> getInvitationsByMeeting(Long meetingId) {
        return invitationRepository.findByMeetingId(meetingId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private InvitationDto convertToDto(Invitation invitation) {
        InvitationDto dto = new InvitationDto();
        dto.setId(invitation.getId());
        dto.setStatus(invitation.getStatus());
        dto.setResponseDate(invitation.getResponseDate());
        dto.setCreatedAt(invitation.getCreatedAt());
        dto.setUpdatedAt(invitation.getUpdatedAt());

        if (invitation.getMeeting() != null) {
            MeetingSimpleDto meetingDto = new MeetingSimpleDto();
            meetingDto.setId(invitation.getMeeting().getId());
            meetingDto.setTitle(invitation.getMeeting().getTitle());
            meetingDto.setMeetingDate(invitation.getMeeting().getMeetingDate());
            meetingDto.setStartTime(invitation.getMeeting().getStartTime());
            meetingDto.setEndTime(invitation.getMeeting().getEndTime());
            meetingDto.setStatus(invitation.getMeeting().getStatus());
            dto.setMeeting(meetingDto);
        }

        if (invitation.getPerson() != null) {
            PersonSimpleDto personDto = new PersonSimpleDto();
            personDto.setId(invitation.getPerson().getId());
            personDto.setName(invitation.getPerson().getName());
            personDto.setEmail(invitation.getPerson().getEmail());
            dto.setPerson(personDto);
        }

        return dto;
    }
}