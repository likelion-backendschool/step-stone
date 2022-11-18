package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByChatRoomEntity(ChatRoomEntity chatRoomEntity);

//    ChatEntity findByChatId(String chatId);
}
