package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
