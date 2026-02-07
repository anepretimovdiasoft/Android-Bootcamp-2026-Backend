package ru.sicampus.bootcamp2026.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.entity.Invitation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Transactional
@ActiveProfiles("tests")
public class InvitationRepositoryTests {
    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MeetingRepository meetingRepository;

    @Test
    void findById() {
        Invitation invitation = invitationRepository.findById(2L).orElse(null);
        assertNotNull(invitation);
        assertEquals("Присутствие необязательно", invitation.getMessage());
    }

    @Test
    void saveInvitation() {
        Invitation invitation = new Invitation();
        invitation.setMessage("Тестовое приглашение");
        invitation.setEmployee(employeeRepository.findById(2L).get());
        invitation.setMeeting(meetingRepository.findById(2L).get());
        Invitation saved = invitationRepository.save(invitation);
        assertNotNull(saved);
        assertEquals("Тестовое приглашение", invitationRepository.findById(saved.getId()).orElse(null).getMessage());
    }
}
