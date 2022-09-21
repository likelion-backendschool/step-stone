package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatRoomInquireEvent {
    private final UserEntity developer;
    private final UserEntity user;

    public ChatRoomInquireEvent(UserEntity developer, UserEntity user) {
        this.developer = developer;
        this.user = user;
    }
}
