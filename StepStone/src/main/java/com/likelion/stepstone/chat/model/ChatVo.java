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

    final Long chatRoomCid;
    final Long senderUserCid;
    final LocalDateTime createdAt;

    public static ChatVo toVo(ChatDto chatDto){
        ChatVo vo = ChatVo.builder()
                .chatRoomCid(chatDto.getChatRoomCid())
                .senderUserCid(chatDto.getSenderUserCid())
                .createdAt(chatDto.getCreatedAt())
                .build();

        return vo;
    }
}
