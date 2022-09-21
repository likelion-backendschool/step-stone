package com.likelion.stepstone.notification;

import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<NotificationEntity ,Long> {
    long countByUserEntityAndChecked(UserEntity userEntity, boolean b);

    List<NotificationEntity> findByUserEntityAndChecked(UserEntity userEntity, boolean b);

    boolean existsByUserEntityAndNotificationTypeAndChecked(UserEntity userEntity, NotificationType notificationType, boolean checked);
}
