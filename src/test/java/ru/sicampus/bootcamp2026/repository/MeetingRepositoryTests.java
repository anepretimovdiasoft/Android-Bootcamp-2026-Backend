package ru.sicampus.bootcamp2026.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.entity.Meeting;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
@ActiveProfiles("tests")
public class MeetingRepositoryTests {
    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void findById() {
        Meeting meeting = meetingRepository.findById(1L).orElse(null);
        assertNotNull(meeting);
        assertEquals("Общее ежедневное собрание", meeting.getName());
    }

    @Test
    void saveMeeting() {
        Meeting meeting = new Meeting();
        meeting.setName("Тестовое собрание");
        meeting.setDescription("Описание тестового собрания");
        meeting.setStartTime(java.time.LocalDateTime.parse("2026-07-01T10:00:00"));
        meeting.setEndTime(java.time.LocalDateTime.parse("2026-07-01T11:00:00"));
        meeting.setOwner(employeeRepository.findById(1L).get());
        Meeting saved = meetingRepository.save(meeting);
        assertNotNull(saved);
        assertEquals("Тестовое собрание", meetingRepository.findById(saved.getId()).orElse(null).getName());
    }

    @Test
    void findByIdAndOwner_Username() {
        Meeting meeting = meetingRepository.findByIdAndOwner_Username(1L, "indexzero");
        assertNotNull(meeting);
        assertEquals("Общее ежедневное собрание", meeting.getName());
    }
}
