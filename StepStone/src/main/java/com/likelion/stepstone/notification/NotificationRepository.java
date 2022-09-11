package com.likelion.stepstone.notification;

import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<NotificationEntity ,Long> {
    long countByUserEntityAndChecked(UserEntity userEntity, boolean b);
}
