package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}
