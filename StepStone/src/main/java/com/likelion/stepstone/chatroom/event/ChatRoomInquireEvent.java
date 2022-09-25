package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatRoomInquireEvent {
    private final UserEntity developer;
    private final UserEntity user;
    private final ChatRoomEntity chatRoomEntity;

    public ChatRoomInquireEvent(UserEntity developer, UserEntity user, ChatRoomEntity chatRoomEntity) {
        this.developer = developer;
        this.user = user;
        this.chatRoomEntity = chatRoomEntity;
    }
}
