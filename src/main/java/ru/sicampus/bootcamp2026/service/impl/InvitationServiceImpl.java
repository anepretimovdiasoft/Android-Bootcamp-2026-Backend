package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;
import ru.sicampus.bootcamp2026.entity.Employee;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.exception.*;
import ru.sicampus.bootcamp2026.repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;
import ru.sicampus.bootcamp2026.util.InvitationMeetingMapper;

import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public InvitationDTO createInvitation(InvitationCreateDTO invitationCreateDTO, String username) {
        Meeting meeting = meetingRepository.findByIdAndOwner_Username(invitationCreateDTO.getMeetingId(), username);
        Employee emp = employeeRepository.findById(invitationCreateDTO.getEmployeeId()).orElse(null);
        if(meeting == null) {
            throw new MeetingNotFoundExeception("Meeting not found");
        }
        if(emp == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        if(invitationRepository.existsByMeeting_IdAndEmployee_Id(invitationCreateDTO.getMeetingId(), invitationCreateDTO.getEmployeeId())) {
            throw new InvitationAlreadyExistsException("Invitation already exists");
        }
        if(invitationRepository.existsByMeeting_StartTimeAndEmployee_IdAndStatus(meeting.getStartTime(), invitationCreateDTO.getEmployeeId(), "ACCEPTED")) {
            throw new InvalidMeetingDateException("Employee is busy at this time");
        }

        Invitation invitation = new Invitation();
        invitation.setMessage(invitationCreateDTO.getMessage());
        invitation.setStatus("PENDING");
        invitation.setMeeting(meeting);
        invitation.setEmployee(emp);

        return InvitationMapper.convertToDTO(invitationRepository.save(invitation));
    }

    @Override
    public InvitationDTO answerInvitation(InvitationAnswerDTO invitationAnswerDTO, String username) {
        Invitation inv = invitationRepository.findById(invitationAnswerDTO.getId()).orElse(null);
        if(inv == null) {
            throw new InvitationNotFoundException("Invitation not found");
        }
        if(!inv.getEmployee().getUsername().equals(username)) {
            throw new InvitationNotOwnedException("Invitation does not owned by the employee");
        }
        if(invitationRepository.existsByEmployee_UsernameAndMeeting_StartTimeAndStatus(username, inv.getMeeting().getStartTime(), "ACCEPTED")
                && invitationAnswerDTO.getStatus().equals("ACCEPTED")) {
            throw new InvalidMeetingDateException("You are busy at this time");
        }

        inv.setStatus(invitationAnswerDTO.getStatus());

        return InvitationMapper.convertToDTO(invitationRepository.save(inv));
    }

    @Override
    public List<InvitationMeetingDTO> getActiveInvitations(String username) {
        return invitationRepository.findByEmployee_UsernameAndStatus(username, "PENDING").stream().map(InvitationMeetingMapper::convertToDTO).toList();
    }
}
