package com.likelion.stepstone.chat.event;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatSendEvent {
    private final String chatRoomId;
    private final UserEntity userEntity;

    public ChatSendEvent(String chatRoomId, UserEntity userEntity) {
        this.chatRoomId = chatRoomId;
        this.userEntity = userEntity;

    }
}
