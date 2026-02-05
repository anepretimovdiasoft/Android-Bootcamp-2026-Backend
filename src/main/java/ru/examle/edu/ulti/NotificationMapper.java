package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.NotificationDTO;
import ru.examle.edu.entity.Notification;

@Component
public class NotificationMapper {
    public NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setMeetingId(notification.getMeetingId());
        dto.setType(notification.getType());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;
        Notification notification = new Notification();
        if (dto.getId() != null) notification.setId(dto.getId());
        if (dto.getUserId() != null) notification.setUserId(dto.getUserId());
        notification.setMeetingId(dto.getMeetingId());
        notification.setType(dto.getType());
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setRead(dto.isRead());
        return notification;
    }
}
