package com.likelion.stepstone.notification.repository;

import com.likelion.stepstone.notification.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NotificationBaseRepository <T extends NotificationEntity> extends JpaRepository<T ,Long> {
}
