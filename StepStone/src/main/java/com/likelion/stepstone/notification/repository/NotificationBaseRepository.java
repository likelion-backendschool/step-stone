package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface NotificationBaseRepository <T extends NotificationEntity> extends JpaRepository<T ,Long> {
    boolean existsByUserEntityAndNotificationTypeAndChecked(UserEntity userEntity, NotificationType notificationType, boolean checked);
    List<T> findByUserEntityAndChecked(UserEntity userEntity, boolean b);
    long countByUserEntityAndChecked(UserEntity userEntity, boolean b);
}
