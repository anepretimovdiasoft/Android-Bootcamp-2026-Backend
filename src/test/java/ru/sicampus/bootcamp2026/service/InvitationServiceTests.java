package ru.sicampus.bootcamp2026.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.exception.EmployeeNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundExeception;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest
@Transactional
public class InvitationServiceTests {
    @Autowired
    private InvitationService invitationService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private MeetingService meetingService;

    @Test
    void createInvitation() {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));
        MeetingDTO meetingDTO = meetingService.createMeeting(meet, "andrey_limasov");

        InvitationCreateDTO inv = new InvitationCreateDTO();
        inv.setMeetingId(meetingDTO.getId());
        inv.setEmployeeUsername("iv_ivan");
        inv.setMessage("Приглашаю тебя на тестовое собрание");

        InvitationDTO invDto = invitationService.createInvitation(inv, "andrey_limasov");
        Invitation invSaved = invitationRepository.findById(invDto.getId()).orElse(null);

        assertNotNull(invSaved);
        assertEquals("Приглашаю тебя на тестовое собрание", invSaved.getMessage());
    }

    @Test
    void createInvitationInvalid() {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));
        MeetingDTO meetingDTO = meetingService.createMeeting(meet, "andrey_limasov");

        InvitationCreateDTO invInvalidUser = new InvitationCreateDTO();
        invInvalidUser.setMeetingId(meetingDTO.getId());
        invInvalidUser.setEmployeeUsername("zzzzzzzzzz");
        invInvalidUser.setMessage("Приглашаю тебя на тестовое собрание");

        InvitationCreateDTO invInvalidMeeting = new InvitationCreateDTO();
        invInvalidMeeting.setMeetingId(1L);
        invInvalidMeeting.setEmployeeUsername("iv_ivan");
        invInvalidMeeting.setMessage("Приглашаю тебя на тестовое собрание");

        Exception exceptionMeetingNotFound = assertThrows(MeetingNotFoundExeception.class, () -> invitationService.createInvitation(invInvalidMeeting, "andrey_limasov"));
        assertTrue(exceptionMeetingNotFound.getMessage().contains("Meeting not found"));

        Exception exceptionEmployeeNotFound = assertThrows(EmployeeNotFoundException.class, () -> invitationService.createInvitation(invInvalidUser, "andrey_limasov"));
        assertTrue(exceptionEmployeeNotFound.getMessage().contains("Employee not found"));
    }

    @Test
    void answerInvitation() {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));
        MeetingDTO meetingDTO = meetingService.createMeeting(meet, "andrey_limasov");

        InvitationCreateDTO inv = new InvitationCreateDTO();
        inv.setMeetingId(meetingDTO.getId());
        inv.setEmployeeUsername("iv_ivan");
        inv.setMessage("Приглашаю тебя на тестовое собрание");

        InvitationDTO invDto = invitationService.createInvitation(inv, "andrey_limasov");

        InvitationAnswerDTO answerDTO = new InvitationAnswerDTO();
        answerDTO.setId(invDto.getId());
        answerDTO.setStatus("ACCEPTED");

        InvitationDTO answeredInv = invitationService.answerInvitation(answerDTO, "iv_ivan");
        assertEquals("ACCEPTED", answeredInv.getStatus());
    }

    @Test
    void getActiveInvitations() {
        List<InvitationMeetingDTO> activeInvitations = invitationService.getActiveInvitations("andrey_limasov");
        assertNotNull(activeInvitations);
        boolean containsMeeting = activeInvitations.stream()
                .anyMatch(invitation -> "Планирование спринта".equals(invitation.getMeeting().getName()));
        assertTrue(containsMeeting);
    }
}
