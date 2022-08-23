package com.likelion.stepstone.chatroom.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {
    @Setter
    UUID chatRoomId;

    Long postCid;

    @Setter
    String roomName;

    @Setter
    Integer userCount;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;



    public static ChatRoomDto toDto(ChatRoomEntity chatRoomEntity){
        ChatRoomDto dto = ChatRoomDto.builder()
                .chatRoomId(chatRoomEntity.getChatRoomId())
                .postCid(chatRoomEntity.getPostCid())
                .roomName(chatRoomEntity.getRoomName())
                .userCount(chatRoomEntity.getUserCount())
                .createdAt(chatRoomEntity.getCreatedAt())
                .updatedAt(chatRoomEntity.getUpdatedAt())
                .build();

        return dto;
    }
}
