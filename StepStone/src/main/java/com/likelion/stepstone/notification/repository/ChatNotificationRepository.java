package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNotificationRepository extends NotificationBaseRepository<ChatNotificationEntity> {

    boolean existsByUserEntityAndNotificationTypeAndChecked(UserEntity userEntity, NotificationType notificationType, boolean checked);
}
