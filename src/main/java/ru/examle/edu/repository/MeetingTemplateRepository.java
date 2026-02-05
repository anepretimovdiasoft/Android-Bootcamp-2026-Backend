package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.MeetingTemplate;

public interface MeetingTemplateRepository extends JpaRepository<MeetingTemplate, Long> {
}
