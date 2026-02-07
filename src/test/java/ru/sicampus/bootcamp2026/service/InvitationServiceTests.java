package ru.sicampus.bootcamp2026.service;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("tests")
@SpringBootTest
@Transactional
public class InvitationServiceTests {
}
