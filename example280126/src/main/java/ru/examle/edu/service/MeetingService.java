package ru.examle.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.*;
import ru.examle.edu.entity.Meeting;
import ru.examle.edu.entity.Person;
import ru.examle.edu.entity.Meeting.MeetingStatus;
import ru.examle.edu.exception.ResourceNotFoundException;
import ru.examle.edu.exception.ValidationException;
import ru.examle.edu.repository.MeetingRepository;
import ru.examle.edu.repository.PersonRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    public List<MeetingDto> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MeetingDto getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + id));
        return convertToDto(meeting);
    }

    @Transactional
    public MeetingDto createMeeting(MeetingRequest request) {
        validateMeetingTime(request);

        Person organizer = personRepository.findById(request.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + request.getOrganizerId()));

        Meeting meeting = new Meeting();
        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setOrganizer(organizer);
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setStartTime(request.getStartTime());
        meeting.setEndTime(request.getEndTime());
        meeting.setStatus(request.getStatus() != null ? request.getStatus() : MeetingStatus.PLANNED);
        meeting.setCreatedAt(LocalDateTime.now());
        meeting.setUpdatedAt(LocalDateTime.now());

        meeting = meetingRepository.save(meeting);
        return convertToDto(meeting);
    }

    @Transactional
    public MeetingDto updateMeeting(Long id, MeetingRequest request) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + id));

        validateMeetingTime(request);

        if (!meeting.getOrganizer().getId().equals(request.getOrganizerId())) {
            Person organizer = personRepository.findById(request.getOrganizerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + request.getOrganizerId()));
            meeting.setOrganizer(organizer);
        }

        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setStartTime(request.getStartTime());
        meeting.setEndTime(request.getEndTime());
        meeting.setStatus(request.getStatus() != null ? request.getStatus() : meeting.getStatus());
        meeting.setUpdatedAt(LocalDateTime.now());

        meeting = meetingRepository.save(meeting);
        return convertToDto(meeting);
    }

    @Transactional
    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + id));

        if (!meeting.getInvitations().isEmpty()) {
            throw new ValidationException("Cannot delete meeting with existing invitations");
        }

        meetingRepository.delete(meeting);
    }

    public List<MeetingDto> getMeetingsByOrganizer(Long organizerId) {
        return meetingRepository.findByOrganizerId(organizerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> getMeetingsByDate(LocalDate date) {
        return meetingRepository.findByMeetingDate(date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> getMeetingsForPerson(Long personId) {
        return meetingRepository.findAllMeetingsForPerson(personId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MeetingDto convertToDto(Meeting meeting) {
        MeetingDto dto = new MeetingDto();
        dto.setId(meeting.getId());
        dto.setTitle(meeting.getTitle());
        dto.setDescription(meeting.getDescription());
        dto.setMeetingDate(meeting.getMeetingDate());
        dto.setStartTime(meeting.getStartTime());
        dto.setEndTime(meeting.getEndTime());
        dto.setStatus(meeting.getStatus());
        dto.setCreatedAt(meeting.getCreatedAt());
        dto.setUpdatedAt(meeting.getUpdatedAt());

        if (meeting.getOrganizer() != null) {
            PersonSimpleDto organizerDto = new PersonSimpleDto();
            organizerDto.setId(meeting.getOrganizer().getId());
            organizerDto.setName(meeting.getOrganizer().getName());
            organizerDto.setEmail(meeting.getOrganizer().getEmail());
            dto.setOrganizer(organizerDto);
        }

        if (meeting.getInvitations() != null) {
            List<InvitationSimpleDto> invitationDtos = meeting.getInvitations().stream()
                    .map(this::convertToSimpleDto)
                    .collect(Collectors.toList());
            dto.setInvitations(invitationDtos);
        }

        return dto;
    }

    private InvitationSimpleDto convertToSimpleDto(ru.examle.edu.entity.Invitation invitation) {
        InvitationSimpleDto dto = new InvitationSimpleDto();
        dto.setId(invitation.getId());
        dto.setStatus(invitation.getStatus());

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

    private void validateMeetingTime(MeetingRequest request) {
        if (request.getEndTime().isBefore(request.getStartTime()) ||
                request.getEndTime().equals(request.getStartTime())) {
            throw new ValidationException("End time must be after start time");
        }

        if (request.getMeetingDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Meeting date cannot be in the past");
        }

        if (request.getStartTime().getMinute() != 0 || request.getStartTime().getSecond() != 0 ||
                request.getEndTime().getMinute() != 0 || request.getEndTime().getSecond() != 0) {
            throw new ValidationException("Meeting times must be at exact hours (minutes and seconds must be 0)");
        }
    }
}