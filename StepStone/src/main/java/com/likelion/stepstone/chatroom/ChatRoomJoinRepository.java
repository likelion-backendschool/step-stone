package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomUserJoinEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomUserJoinId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomUserJoinEntity, ChatRoomUserJoinId> {
    List<ChatRoomUserJoinEntity> findByIdChatRoomCid(Long chatRoomCid);
}
