package ru.sicampus.bootcamp2026.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.sicampus.bootcamp2026.dto.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("tests")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class InvitationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInvitation() throws Exception {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));

        MvcResult result = this.mockMvc.perform(
                        post("/api/meeting")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(meet))
                )
                .andDo(print())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        MeetingDTO meetingDTO = objectMapper.readValue(json, MeetingDTO.class);

        InvitationCreateDTO inv = new InvitationCreateDTO();
        inv.setMeetingId(meetingDTO.getId());
        inv.setEmployeeUsername("iv_ivan");
        inv.setMessage("Приглашаю тебя на тестовое собрание");

        this.mockMvc.perform(
                        post("/api/invitation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(inv))
                )
                .andDo(print())
                .andExpect(status().isCreated()
                );
    }

    @Test
    void createInvitationInvalid() throws Exception {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));

        MvcResult result = this.mockMvc.perform(
                        post("/api/meeting")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(meet))
                )
                .andDo(print())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        MeetingDTO meetingDTO = objectMapper.readValue(json, MeetingDTO.class);

        InvitationCreateDTO invInvalidUser = new InvitationCreateDTO();
        invInvalidUser.setMeetingId(meetingDTO.getId());
        invInvalidUser.setEmployeeUsername("zzzzzzzzzz");
        invInvalidUser.setMessage("Приглашаю тебя на тестовое собрание");

        InvitationCreateDTO invInvalidMeeting = new InvitationCreateDTO();
        invInvalidMeeting.setMeetingId(1L);
        invInvalidMeeting.setEmployeeUsername("iv_ivan");
        invInvalidMeeting.setMessage("Приглашаю тебя на тестовое собрание");

        this.mockMvc.perform(
                        post("/api/invitation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(invInvalidUser))
                )
                .andDo(print())
                .andExpect(status().isNotFound()
                );

        this.mockMvc.perform(
                        post("/api/invitation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(invInvalidMeeting))
                )
                .andDo(print())
                .andExpect(status().isConflict()
                );
    }

    @Test
    void answerInvitation() throws Exception {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));

        MvcResult result = this.mockMvc.perform(
                        post("/api/meeting")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(meet))
                )
                .andDo(print())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        MeetingDTO meetingDTO = objectMapper.readValue(json, MeetingDTO.class);

        InvitationCreateDTO inv = new InvitationCreateDTO();
        inv.setMeetingId(meetingDTO.getId());
        inv.setEmployeeUsername("iv_ivan");
        inv.setMessage("Приглашаю тебя на тестовое собрание");

        MvcResult resultInv = this.mockMvc.perform(
                        post("/api/invitation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(inv))
                )
                .andDo(print())
                .andReturn();
        String jsonInv = resultInv.getResponse().getContentAsString();
        InvitationDTO invitationDTO = objectMapper.readValue(jsonInv, InvitationDTO.class);

        InvitationAnswerDTO answerDTO = new InvitationAnswerDTO();
        answerDTO.setId(invitationDTO.getId());
        answerDTO.setStatus("ACCEPTED");

        this.mockMvc.perform(
                        patch("/api/invitation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("iv_ivan", "1234561234"))
                                .content(objectMapper.writeValueAsString(answerDTO))
                )
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void getActiveInvitations() throws Exception {
        MvcResult result = this.mockMvc.perform(
                        get("/api/invitation/active")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<InvitationMeetingDTO> invitations = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, InvitationMeetingDTO.class)
        );
        boolean containsMeeting = invitations.stream()
                .anyMatch(invitation -> "Планирование спринта".equals(invitation.getMeeting().getName()));
        assertTrue(containsMeeting);
    }
}
