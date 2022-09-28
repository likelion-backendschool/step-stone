package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomUserJoinEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomUserJoinId;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomUserJoinEntity, ChatRoomUserJoinId> {
    List<ChatRoomUserJoinEntity> findByIdChatRoomCid(Long chatRoomCid);

    List<ChatRoomUserJoinEntity> findByIdUserCid(Long userCid);

    Optional<ChatRoomUserJoinEntity> findByChatRoomEntityAndUserEntity(ChatRoomEntity chatRoomEntity, UserEntity userEntity);
    List<ChatRoomUserJoinEntity> findByChatRoomEntity(ChatRoomEntity chatRoomEntity);

    boolean existsByUserEntityAndChatRoomEntity(UserEntity userEntity, ChatRoomEntity chatRoomEntity);
}
