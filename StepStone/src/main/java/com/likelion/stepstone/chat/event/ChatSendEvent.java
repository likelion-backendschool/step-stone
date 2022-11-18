package com.likelion.stepstone.chat.event;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Getter;

@Getter
public class ChatSendEvent {
    private final ChatEntity chatEntity;
    private final UserEntity userEntity;

    public ChatSendEvent(ChatEntity chatEntity, UserEntity userEntity) {
        this.chatEntity = chatEntity;
        this.userEntity = userEntity;

    }
}
