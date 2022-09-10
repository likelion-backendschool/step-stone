package com.likelion.stepstone.notification.model;

import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

public class NotificationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne
    private UserEntity userEntity;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
