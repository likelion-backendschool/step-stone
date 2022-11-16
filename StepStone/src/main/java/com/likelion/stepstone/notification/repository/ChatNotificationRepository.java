package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNotificationRepository extends NotificationBaseRepository<ChatNotificationEntity> {
}
