package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.redis.RedisChatRepository;
import com.likelion.stepstone.chat.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat") // MessageMapping에는 미적용
public class ChatController {

    private final ChatService chatService;
    private final RedisPublisher redisPublisher;
    private final ChannelTopic channelTopic;
    private final RedisChatRepository redisChatRepository;

    @MessageMapping(value = "/chat/message")
    void sendMessage(ChatDto chatDto){
//        chatDto.setSenderUserCid(1l);

//        chatService.sendMessage(chatDto);
        redisPublisher.publish(channelTopic.getTopic(), chatDto);
        redisChatRepository.createChat(chatDto);
    }

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatDto chatDto){
        chatService.enter(chatDto);
    }
}
