package ru.examle.edu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.examle.edu.repository.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Application is running!");
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStatistics() {
        Map<String, Long> stats = Map.of(
                "departments", departmentRepository.count(),
                "persons", personRepository.count(),
                "meetings", meetingRepository.count(),
                "invitations", invitationRepository.count()
        );
        return ResponseEntity.ok(stats);
    }
}