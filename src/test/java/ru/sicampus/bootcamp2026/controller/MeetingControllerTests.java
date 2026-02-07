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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("tests")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MeetingControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMeeting() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/meeting/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        MeetingDTO meet = objectMapper.readValue(json, MeetingDTO.class);

        assertEquals("Общее ежедневное собрание", meet.getName());
    }

    @Test
    void createMeeting() throws Exception {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));

        this.mockMvc.perform(
                        post("/api/meeting/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(meet))
                )
                .andDo(print())
                .andExpect(status().isOk()
        );
    }

    @Test
    void createMeetingInvalid() throws Exception {
        MeetingCreateDTO meetPastDate = new MeetingCreateDTO();
        meetPastDate.setName("UNIX Development Team Daily Meeting");
        meetPastDate.setDescription("Ежедневное собрание команды разработки UNIX");
        meetPastDate.setStartTime(LocalDateTime.parse("1970-01-01T10:00:00"));
        meetPastDate.setEndTime(LocalDateTime.parse("1970-01-01T11:00:00"));

        MeetingCreateDTO meetInvalid = new MeetingCreateDTO();
        meetInvalid.setName("");
        meetInvalid.setDescription("");
        meetInvalid.setStartTime(LocalDateTime.parse("1970-01-01T11:00:00"));
        meetInvalid.setEndTime(LocalDateTime.parse("1970-01-01T11:00:00"));

        this.mockMvc.perform(
                        post("/api/meeting/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(meetPastDate))
                )
                .andDo(print())
                .andExpect(status().isBadRequest()
        );

        this.mockMvc.perform(
                        post("/api/meeting/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                                .content(objectMapper.writeValueAsString(meetInvalid))
                )
                .andDo(print())
                .andExpect(status().isBadRequest()
                );
    }

    @Test
    void getMeetingNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/api/meeting/111111111")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getMeetingParticipants() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/meeting/1/participants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<InvitationEmployeeDTO> participants = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, InvitationEmployeeDTO.class)
        );
        boolean containsIndexZero = participants.stream()
                .anyMatch(participant -> "indexzero".equals(participant.getEmployee().getUsername()));

        assertEquals(true, containsIndexZero);
    }
    @Test
    void getMeetingSchedule() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/meeting/schedule?start=2026-01-01T00:00:00&end=2026-12-31T00:00:00")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("andrey_limasov", "1234561234"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<MeetingDTO> meetings = objectMapper.readValue(
                json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, MeetingDTO.class)
        );
        boolean containsMeeting = meetings.stream()
                .anyMatch(meeting -> "Ретроспектива спринта".equals(meeting.getName()));

        assertEquals(true, containsMeeting);
    }
}
