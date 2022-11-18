package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.ChatNotificationDto;
import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Repository
@Transactional(readOnly = true)
public interface ChatNotificationRepository extends NotificationBaseRepository<ChatNotificationEntity> {

    boolean existsByUserEntityAndNotificationTypeAndChecked(UserEntity userEntity, NotificationType notificationType, boolean checked);
    List<ChatNotificationEntity> findByUserEntityAndChecked(UserEntity userEntity, boolean b);
}
