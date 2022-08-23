package com.likelion.stepstone.chat.model;


import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
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
@Table(name = "chats")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_cid")
    Long chatCid;

    @Setter
    @Column(name = "message")
    String message;

    @Setter
    @Column(name = "chat_room_id")
    String chatRoomId;

    @ManyToOne
    @Setter
    UserEntity sender;


    @Setter
    @Column(name = "created_at")
    @CreatedDate
    LocalDateTime createdAt;

    public static ChatEntity toEntity(ChatDto chatDto){
        ChatEntity entity = ChatEntity.builder()
                .chatRoomId(chatDto.getChatRoomId())
                .message(chatDto.getMessage())
                .createdAt(chatDto.getCreatedAt())
                .build();

        return entity;
    }

}
