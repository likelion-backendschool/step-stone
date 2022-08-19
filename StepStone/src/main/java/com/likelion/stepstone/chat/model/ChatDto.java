package com.likelion.stepstone.chat.model;

import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    @Setter
    Long chatRoomCid;
    @Setter
    Long senderUserCid;

    LocalDateTime createdAt;



    public static ChatDto toDto(ChatEntity chatEntity){
        ChatDto dto = ChatDto.builder()
                .chatRoomCid(chatEntity.getChatRoomCid())
                .senderUserCid(chatEntity.getSenderUserCid())
                .createdAt(chatEntity.getCreatedAt())
                .build();

        return dto;
    }
}
