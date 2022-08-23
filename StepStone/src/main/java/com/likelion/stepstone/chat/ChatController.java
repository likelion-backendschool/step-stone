package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;
    @MessageMapping
    void sendMessage(ChatDto chatDto){
        chatDto.setSenderUserCid(1l);

        chatService.sendMessage(chatDto);
    }

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatDto chatDto){
        chatService.enter(chatDto);
    }
}
