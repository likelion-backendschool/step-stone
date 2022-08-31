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
    String chatRoomId;

    @Setter
    String senderId;

    @Setter
    String senderName;

    @Setter
    String message;

    String createdAt;

    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;

    public static ChatDto toDto(ChatEntity chatEntity){
        ChatDto dto = ChatDto.builder()
                .chatRoomId(chatEntity.getChatRoomId())
                .message(chatEntity.getMessage())
                .senderId(chatEntity.getSender().getUserId())
                .senderName(chatEntity.getSender().getName())
                .createdAt(chatEntity.getCreatedAt())
                .build();

        return dto;
    }
}
