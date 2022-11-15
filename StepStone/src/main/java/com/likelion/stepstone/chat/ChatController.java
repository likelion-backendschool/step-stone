package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat") // MessageMapping에는 미적용
public class ChatController {

    private final ChatService chatService;



    @MessageMapping(value = "/chat/message")
    void sendMessage(ChatDto chatDto){
//        chatDto.setSenderUserCid(1l);

        chatService.sendMessage(chatDto);
    }

    @PostMapping("/message")
    @ResponseBody
    public void postMessage(@RequestBody ChatDto chatDto){
        chatService.sendMessage(chatDto);
    }

    @GetMapping(value="/last/{chatRoomId}")
    @ResponseBody
    public Flux<ServerSentEvent<ChatDto>> getChat(@PathVariable("chatRoomId") String chatRoomId){
        return chatService.createSubscriber(chatRoomId);
    }

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatDto chatDto){
        chatService.enter(chatDto);
    }
}
