package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomVo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomVo create(ChatRoomDto chatRoomDto) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toEntity(chatRoomDto);

        chatRoomRepository.save(chatRoomEntity);

        return ChatRoomVo.toVo(ChatRoomDto.toDto(chatRoomEntity));
    }
}
