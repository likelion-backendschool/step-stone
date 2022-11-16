package com.likelion.stepstone.notification.model;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chat_notifications")
public class ChatNotificationEntity extends NotificationEntity{

    @ManyToOne
    private ChatRoomEntity chatRoomEntity;
    @ManyToOne
    private UserEntity publisher;
}
