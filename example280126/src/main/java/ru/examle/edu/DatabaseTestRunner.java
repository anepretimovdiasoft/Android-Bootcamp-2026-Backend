package ru.examle.edu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.entity.Department;
import ru.examle.edu.entity.Person;
import ru.examle.edu.repository.*;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DatabaseTestRunner implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("=== DATABASE INTEGRATION TEST ===");

        long deptCount = departmentRepository.count();
        long personCount = personRepository.count();
        long meetingCount = meetingRepository.count();
        long invitationCount = invitationRepository.count();

        log.info("Departments in DB: {}", deptCount);
        log.info("Persons in DB: {}", personCount);
        log.info("Meetings in DB: {}", meetingCount);
        log.info("Invitations in DB: {}", invitationCount);

        if (deptCount > 0 && personCount > 0) {
            log.info("✓ CSV данные успешно загружены");
        } else {
            log.error("✗ CSV данные не загрузились!");
        }

        List<Department> departments = departmentRepository.findAll();
        departments.forEach(dept ->
                log.info("Department: {} (ID: {})", dept.getName(), dept.getId())
        );

        log.info("\n=== Persons with Departments ===");
        List<Person> persons = personRepository.findAll();
        for (Person person : persons) {
            String deptName = person.getDepartment().getName();
            log.info("Person: {} - {} ({})",
                    person.getName(),
                    person.getEmail(),
                    deptName);
        }

        log.info("=== DATABASE TEST COMPLETED ===");
    }
}