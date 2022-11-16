package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationRepository extends NotificationBaseRepository<NotificationEntity>{
    long countByUserEntityAndChecked(UserEntity userEntity, boolean b);

    List<NotificationEntity> findByUserEntityAndChecked(UserEntity userEntity, boolean b);

    boolean existsByUserEntityAndNotificationTypeAndChecked(UserEntity userEntity, NotificationType notificationType, boolean checked);
}
