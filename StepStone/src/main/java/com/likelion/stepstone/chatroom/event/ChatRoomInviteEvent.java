package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatRoomInviteEvent {
    private final ChatRoomEntity chatRoomEntity;
    private final UserEntity userEntity;

    public ChatRoomInviteEvent(ChatRoomEntity chatRoomEntity, UserEntity userEntity) {
        this.chatRoomEntity = chatRoomEntity;
        this.userEntity = userEntity;

    }
}
