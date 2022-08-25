package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByChatRoomId(String chatRoomId);

    ChatEntity findByChatId(String chatId);
}
