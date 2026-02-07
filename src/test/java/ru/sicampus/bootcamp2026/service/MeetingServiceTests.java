package ru.sicampus.bootcamp2026.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.exception.InvalidMeetingDateException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("tests")
@SpringBootTest
@Transactional
public class MeetingServiceTests {
    @Autowired
    MeetingService meetingService;

    @Autowired
    MeetingRepository meetingRepository;

    @Test
    void createMeeting() {
        MeetingCreateDTO meet = new MeetingCreateDTO();
        meet.setName("Тестовое собрание");
        meet.setDescription("Описание тестового собрания");
        meet.setStartTime(LocalDateTime.parse("2026-07-01T10:00:00"));
        meet.setEndTime(LocalDateTime.parse("2026-07-01T11:00:00"));

        MeetingDTO createMeet = meetingService.createMeeting(meet, "andrey_limasov");

        Meeting created = meetingRepository.findById(createMeet.getId()).orElse(null);

        assertNotNull(created);
        assertEquals(meet.getName(), created.getName());
    }

    @Test
    void createMeetingInvalid() {
        MeetingCreateDTO meetPastDate = new MeetingCreateDTO();
        meetPastDate.setName("UNIX Development Team Daily Meeting");
        meetPastDate.setDescription("Ежедневное собрание команды разработки UNIX");
        meetPastDate.setStartTime(LocalDateTime.parse("1970-01-01T10:00:00"));
        meetPastDate.setEndTime(LocalDateTime.parse("1970-01-01T11:00:00"));

        Exception exception = assertThrows(InvalidMeetingDateException.class, () -> meetingService.createMeeting(meetPastDate, "andrey_limasov"));
        assertTrue(exception.getMessage().contains("Invalid start or end time"));
    }

    @Test
    void getMeetingByID() {
        MeetingDTO meeting = meetingService.getMeetingByID(1L);

        assertNotNull(meeting);
        assertEquals("Общее ежедневное собрание", meeting.getName());
    }

    @Test
    void getMeetingByIDNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> meetingService.getMeetingByID(999L));
        assertTrue(exception.getMessage().contains("Meeting not found"));
    }

    @Test
    void getMeetingParticipants() {
        List<InvitationEmployeeDTO> participants = meetingService.getEmployeesByMeetingID(1L);
        assertNotNull(participants);
        boolean containsIndexZero = participants.stream()
                .anyMatch(participant -> "indexzero".equals(participant.getEmployee().getUsername()));
        assertTrue(containsIndexZero);
    }

    @Test
    void getMeetingParticipantsNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> meetingService.getEmployeesByMeetingID(999L));
        assertTrue(exception.getMessage().contains("Meeting not found"));
    }

    @Test
    void getMeetingSchedule() {
        LocalDateTime start = LocalDateTime.parse("2026-01-01T00:00:00");
        LocalDateTime end = LocalDateTime.parse("2026-12-31T00:00:00");

        List<MeetingDTO> schedule = meetingService.getSchedule(start, end, "andrey_limasov");
        assertNotNull(schedule);
        boolean containsMeeting = schedule.stream()
                .anyMatch(meeting -> "Ретроспектива спринта".equals(meeting.getName()));

        assertTrue(containsMeeting);
    }
}
