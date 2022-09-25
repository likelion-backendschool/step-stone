package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatRoomInviteEvent {
    private final ChatRoomEntity chatRoomEntity;
    private final UserEntity userEntity;
    private final UserEntity publisher;

    public ChatRoomInviteEvent(ChatRoomEntity chatRoomEntity, UserEntity userEntity, UserEntity publisher) {
        this.chatRoomEntity = chatRoomEntity;
        this.userEntity = userEntity;
        this.publisher = publisher;
    }
}
