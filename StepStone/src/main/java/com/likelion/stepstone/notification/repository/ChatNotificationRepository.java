package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatNotificationRepository extends NotificationBaseRepository<ChatNotificationEntity> {

    boolean existsByUserEntityAndNotificationTypeAndChecked(UserEntity userEntity, NotificationType notificationType, boolean checked);
    List<NotificationEntity> findByUserEntityAndChecked(UserEntity userEntity, boolean b);
}
