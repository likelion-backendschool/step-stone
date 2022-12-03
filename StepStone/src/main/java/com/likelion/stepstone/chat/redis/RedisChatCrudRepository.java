package com.likelion.stepstone.chat.redis;

import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chat.model.RedisChatEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedisChatCrudRepository extends CrudRepository<RedisChatEntity, String> {
    List<RedisChatEntity> findByChatRoomId(String chatRoomId);
}
