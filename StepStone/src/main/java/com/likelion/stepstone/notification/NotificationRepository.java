package com.likelion.stepstone.notification;

import com.likelion.stepstone.notification.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<NotificationEntity ,Long> {

}
