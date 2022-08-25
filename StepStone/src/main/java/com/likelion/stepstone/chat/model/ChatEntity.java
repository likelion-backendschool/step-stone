package com.likelion.stepstone.chat.model;


import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chats")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_cid")
    Long chatCid;

    @Setter
    @Column(name = "chat_room_id")
    String chatRoomId;

    @Setter
    @Column(name = "message")
    String message;


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
