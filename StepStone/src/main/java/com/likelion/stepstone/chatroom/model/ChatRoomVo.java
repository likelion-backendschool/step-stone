package com.likelion.stepstone.chatroom.model;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostVo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class ChatRoomVo {
    final String chatRoomId;

    final Long postCid;

    final String roomName;

    final Integer userCount;

    final String imageUrl;

    final LocalDateTime createdAt;

    final LocalDateTime updatedAt;

    public static ChatRoomVo toVo(ChatRoomDto chatRoomDto) {
        ChatRoomVo vo = ChatRoomVo.builder()
                .chatRoomId(chatRoomDto.getChatRoomId())
                .postCid(chatRoomDto.getPostCid())
                .roomName(chatRoomDto.getRoomName())
                .userCount(chatRoomDto.getUserCount())
                .createdAt(chatRoomDto.getCreatedAt())
                .updatedAt(chatRoomDto.getUpdatedAt())
                .build();

        return vo;
    }
}
