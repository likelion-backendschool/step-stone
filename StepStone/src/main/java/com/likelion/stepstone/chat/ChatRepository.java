package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByChatRoomId(String chatRoomId);

    ChatEntity findByChatId(String chatId);

    Optional<ChatEntity> findTopByChatRoomIdOrderByChatCidDesc(String ChatRoomId);
}
