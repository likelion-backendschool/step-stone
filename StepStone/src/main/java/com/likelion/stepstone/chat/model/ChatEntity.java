package com.likelion.stepstone.chat.model;


import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
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
    @Column(name = "chat_room_cid")
    Long chatRoomCid;

    @Setter
    @Column(name = "sender_user_cid")
    Long senderUserCid;

    @Setter
    @Column(name = "created_at")
    @CreatedDate
    LocalDateTime createdAt;

    public static ChatEntity toEntity(ChatDto chatDto){
        ChatEntity entity = ChatEntity.builder()
                .chatRoomCid(chatDto.getChatRoomCid())
                .senderUserCid(chatDto.getSenderUserCid())
                .createdAt(chatDto.getCreatedAt())
                .build();

        return entity;
    }

}
