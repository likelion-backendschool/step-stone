package com.likelion.stepstone.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class ChatVo {

    final String chatRoomId;
    final String message;
    final String senderId;
    final String senderName;
    final String createdAt;

//    public static ChatVo toVo(ChatEntity chatEntity){
//        ChatVo vo = ChatVo.builder()
//                .chatRoomId(chatEntity.getChatRoomId())
//                .message(chatEntity.getMessage())
//                .senderId(chatEntity.getSender().getUserId())
//                .senderName(chatEntity.getSender().getName())
//                .createdAt(chatEntity.getCreatedAt())
//                .build();
//
//        return vo;
//    }
}
