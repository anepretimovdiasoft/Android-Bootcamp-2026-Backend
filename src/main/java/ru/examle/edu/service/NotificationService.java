package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.NotificationDTO;

public interface NotificationService {
    Page<NotificationDTO> getAllNotifications(Pageable pageable);
    NotificationDTO getNotificationById(Long id);
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO);
    void deleteNotification(Long id);
}
