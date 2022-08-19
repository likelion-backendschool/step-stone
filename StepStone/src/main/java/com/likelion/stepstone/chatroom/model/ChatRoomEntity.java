package com.likelion.stepstone.chatroom.model;


import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "chat_rooms")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_room_cid")
    Long chatRoomCid;

    @Setter
    @Column(name = "chat_room_id")
    @Type(type = "uuid-char")
    UUID chatRoomId;

    @Setter
    @Column(name = "post_cid")
    Long postCid;

    @Setter
    @Column(name = "room_name")
    String roomName;

    @Setter
    @Column(name = "user_count")
    Integer userCount;

    @Setter
    @Column(name = "created_at")
    @CreatedDate
    LocalDateTime createdAt;

    public static ChatRoomEntity toEntity(ChatRoomDto chatRoomDto){
        ChatRoomEntity entity = ChatRoomEntity.builder()
                .chatRoomId(chatRoomDto.getChatRoomId())
                .postCid(chatRoomDto.getPostCid())
                .roomName(chatRoomDto.getRoomName())
                .userCount(chatRoomDto.getUserCount())
                .createdAt(chatRoomDto.getCreatedAt())
                .build();

        return entity;
    }
}
