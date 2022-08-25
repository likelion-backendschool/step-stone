package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/history") // 저장된 채팅 내역 조회
    String getHistory(Model model, String roomId) {
        List<ChatDto> chats = chatService.getHistories(roomId);

        model.addAttribute("chats", chats);

        return "chat/room :: #chats";
    }

    @MessageMapping
    void sendMessage(ChatDto chatDto){
//        chatDto.setSenderUserCid(1l);

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
