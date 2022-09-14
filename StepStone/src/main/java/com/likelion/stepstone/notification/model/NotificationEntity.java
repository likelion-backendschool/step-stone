package com.likelion.stepstone.notification.model;

import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_cid")
    private Long id;

    @Column(name="title")
    private String title;
    @Column(name="message")
    private String message;
    @Column(name="checked")
    private boolean checked;

    @ManyToOne
    private UserEntity userEntity;

    @Column(name="created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    public static NotificationEntity toEntity(NotificationDto dto){
        NotificationEntity entity = NotificationEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .checked(dto.isChecked())
                .userEntity(dto.getUserEntity())
                .createdAt(dto.getCreatedAt())
                .notificationType(dto.getNotificationType())
                .build();

        return entity;
    }

    public void read(){
        this.checked = true;
    }
}
