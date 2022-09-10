package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import lombok.Getter;

@Getter
public class ChatRoomCreatedEvent {
    private final ChatRoomEntity chatRoomEntity;

    public ChatRoomCreatedEvent(ChatRoomEntity chatRoomEntity) {
        this.chatRoomEntity = chatRoomEntity;
    }
}
