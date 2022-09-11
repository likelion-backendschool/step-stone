package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatRoomCreatedEvent {
    private final ChatRoomEntity chatRoomEntity;
    private final UserEntity userEntity;

    public ChatRoomCreatedEvent(ChatRoomEntity chatRoomEntity, UserEntity userEntity) {
        this.chatRoomEntity = chatRoomEntity;
        this.userEntity = userEntity;

    }
}
