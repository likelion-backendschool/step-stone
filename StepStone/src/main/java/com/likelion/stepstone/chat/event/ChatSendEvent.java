package com.likelion.stepstone.chat.event;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatSendEvent {
    private final ChatRoomEntity chatRoomEntity;
    private final UserEntity userEntity;

    public ChatSendEvent(ChatRoomEntity chatRoomEntity, UserEntity userEntity) {
        this.chatRoomEntity = chatRoomEntity;
        this.userEntity = userEntity;

    }
}
