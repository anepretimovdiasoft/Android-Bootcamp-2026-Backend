package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Employee;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.exception.InvalidMeetingDateException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundExeception;
import ru.sicampus.bootcamp2026.repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.InvitationEmployeeMapper;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.MeetingValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public MeetingDTO createMeeting(MeetingCreateDTO meetingCreateDTO, String username) {
        if(!MeetingValidator.validateStartEnd(meetingCreateDTO.getStartTime(), meetingCreateDTO.getEndTime())) {
            throw new InvalidMeetingDateException("Invalid start or end time");
        }
        if(meetingRepository.existsByOwner_UsernameAndStartTime(username, meetingCreateDTO.getStartTime())) {
            throw new InvalidMeetingDateException("You already have a meeting at this time");
        }
        if(meetingRepository.existsByInvitations_Employee_UsernameAndStartTime(username, meetingCreateDTO.getStartTime())) {
            throw new InvalidMeetingDateException("You already have a meeting at this time");
        }

        Employee emp = employeeRepository.findByUsername(username);

        Meeting meeting = new Meeting();
        meeting.setName(meetingCreateDTO.getName());
        meeting.setDescription(meetingCreateDTO.getDescription());
        meeting.setStartTime(meetingCreateDTO.getStartTime());
        meeting.setEndTime(meetingCreateDTO.getEndTime());
        meeting.setOwner(emp);
        meetingRepository.save(meeting);

        Invitation invitation = new Invitation();
        invitation.setEmployee(emp);
        invitation.setMeeting(meeting);
        invitation.setStatus("ACCEPTED");

        invitationRepository.save(invitation);


        return MeetingMapper.convertToDTO(meeting);
    }

    @Override
    public MeetingDTO getMeetingByID(Long id) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if (meeting.isEmpty()) {
            throw new MeetingNotFoundExeception("Meeting not found");
        }
        return MeetingMapper.convertToDTO(meeting.get());
    }

    @Override
    public List<MeetingDTO> getSchedule(LocalDateTime start, LocalDateTime end, String username) {
//        List<MeetingDTO> schedule = invitationRepository.findByEmployee_UsernameAndStatus(username, "ACCEPTED").stream().filter(i -> {
//            LocalDateTime startTime = i.getMeeting().getStartTime();
//            LocalDateTime endTime = i.getMeeting().getEndTime();
//            return startTime.isAfter(start) && endTime.isBefore(end);
//        }).map(Invitation::getMeeting).map(MeetingMapper::convertToDTO).toList();

//        List<MeetingDTO> schedule = meetingRepository.findByInvitations_Employee_UsernameAndInvitations_StatusAndStartTimeBetween(username, "ACCEPTED", start, end).stream()
//                .map(Invitation::getMeeting).map(MeetingMapper::convertToDTO).toList();

        List<MeetingDTO> schedule = meetingRepository.findByInvitations_Employee_UsernameAndInvitations_StatusAndStartTimeBetween(username, "ACCEPTED", start, end).stream()
                .map(MeetingMapper::convertToDTO).toList();

        return schedule;
    }

    @Override
    public List<InvitationEmployeeDTO> getEmployeesByMeetingID(Long id) {
        Meeting meeting = meetingRepository.findMeetingById(id);
        if (meeting == null) {
            throw new MeetingNotFoundExeception("Meeting not found");
        }

        return meeting.getInvitations().stream().map(InvitationEmployeeMapper::convertToDTO).toList();
    }

    @Override
    public void deleteById(Long id, String username) {
        Meeting meeting = meetingRepository.findByIdAndOwner_Username(id, username);
        if (meeting == null) {
            throw new MeetingNotFoundExeception("Meeting not found in your meetings");
        }
        meetingRepository.deleteById(id);
    }
}
