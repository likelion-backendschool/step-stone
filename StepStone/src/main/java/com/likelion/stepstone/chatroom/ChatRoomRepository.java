package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, ChatRoomEntity> {
    List<ChatRoomEntity> findByUsers_UserCid(Long userCid);
    Optional<ChatRoomEntity> findByChatRoomId(String chatRoomId);
}
