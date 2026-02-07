package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.*;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public InvitationDTO createInvitation(InvitationCreateDTO dto, String username) {
        Meeting meeting = meetingRepository.findByIdAndOwner_Username(dto.getMeetingId(), username);
        Employee emp = employeeRepository.findByUsername(dto.getEmployeeUsername());
        if(meeting == null) {
            throw new MeetingNotOwnedException("Meeting does not exist or is not owned by you");
        }
        if(emp == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        if(invitationRepository.existsByMeeting_IdAndEmployee_Username(dto.getMeetingId(), dto.getEmployeeUsername())) {
            throw new InvitationAlreadyExistsException("Invitation already exists");
        }
        if(invitationRepository.existsByMeeting_StartTimeAndEmployee_UsernameAndStatus(meeting.getStartTime(), dto.getEmployeeUsername(), "ACCEPTED")) {
            throw new InvalidMeetingDateException("Employee is busy at this time");
        }

        Invitation invitation = new Invitation();
        invitation.setMessage(dto.getMessage());
        invitation.setStatus("PENDING");
        invitation.setMeeting(meeting);
        invitation.setEmployee(emp);

        return InvitationMapper.convertToDTO(invitationRepository.save(invitation));
    }

    @Override
    public List<InvitationDTO> createInvitationsBatch(InvitationCreateBatchDTO dto, String username) {
        Meeting meeting = meetingRepository.findByIdAndOwner_Username(dto.getMeetingId(), username);
        ArrayList<Invitation> invitations = new ArrayList<>();
        if(meeting == null) {
            throw new MeetingNotOwnedException("Meeting does not exist or is not owned by you");
        }
        for(int i = 0; i < dto.getEmployeeUsernames().size(); i++) {
            Employee emp = employeeRepository.findByUsername(dto.getEmployeeUsernames().get(i));
            if(emp == null) {
                throw new EmployeeNotFoundException("Employee not found: " + dto.getEmployeeUsernames().get(i) + ". Please check the selected users");
            }
            if(invitationRepository.existsByMeeting_IdAndEmployee_Username(dto.getMeetingId(), dto.getEmployeeUsernames().get(i))) {
                throw new InvitationAlreadyExistsException("Invitation already exists for employee: " + dto.getEmployeeUsernames().get(i) + ". Please check the selected users");
            }
            if(invitationRepository.existsByMeeting_StartTimeAndEmployee_UsernameAndStatus(meeting.getStartTime(), dto.getEmployeeUsernames().get(i), "ACCEPTED")) {
                throw new InvalidMeetingDateException("Employee is busy at this time: " + dto.getEmployeeUsernames().get(i) + ". Please check the selected users");
            }
            Invitation invitation = new Invitation();
            invitation.setMessage(dto.getMessage());
            invitation.setStatus("PENDING");
            invitation.setMeeting(meeting);
            invitation.setEmployee(emp);
            invitations.add(invitation);
        }
        return invitationRepository.saveAllAndFlush(invitations).stream().map(InvitationMapper::convertToDTO).toList();
    }

    @Override
    public InvitationDTO answerInvitation(InvitationAnswerDTO dto, String username) {
        Invitation inv = invitationRepository.findById(dto.getId()).orElse(null);
        if(inv == null) {
            throw new InvitationNotFoundException("Invitation not found");
        }
        if(!inv.getEmployee().getUsername().equals(username)) {
            throw new InvitationNotOwnedException("Invitation does not owned by the employee");
        }
        if(invitationRepository.existsByEmployee_UsernameAndMeeting_StartTimeAndStatus(username, inv.getMeeting().getStartTime(), "ACCEPTED")
                && dto.getStatus().equals("ACCEPTED")) {
            throw new InvalidMeetingDateException("You are busy at this time");
        }

        inv.setStatus(dto.getStatus());

        return InvitationMapper.convertToDTO(invitationRepository.save(inv));
    }

    @Override
    public List<InvitationMeetingDTO> getActiveInvitations(String username) {
        return invitationRepository.findByEmployee_UsernameAndStatus(username, "PENDING").stream().map(InvitationMeetingMapper::convertToDTO).toList();
    }
}
