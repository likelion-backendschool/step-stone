package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findByUsers_UserCid(Long userCid);
    Optional<ChatRoomEntity> findByChatRoomId(String chatRoomId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE ChatRoomEntity chatroom SET chatroom.userCount = :userCount where chatroom.chatRoomCid = :chatRoomId")
    int updateUserCount(@Param(value="userCount") Integer userCount, @Param(value="chatRoomId") Long chatRoomId);

    boolean existsByChatRoomId(String chatRoomId);
}
