package com.likelion.stepstone.stepstone.chat;

import com.likelion.stepstone.chat.ChatRepository;
import com.likelion.stepstone.chat.ChatService;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chat.model.RedisChatEntity;
import com.likelion.stepstone.chat.redis.RedisChatCrudRepository;
import com.likelion.stepstone.chat.redis.RedisChatRepository;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.ChatRoomService;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomVo;
import com.likelion.stepstone.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
public class ChatServiceTest {
    @Autowired
    ChatService chatService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    RedisChatRepository redisChatRepository;

    @Autowired
    RedisChatCrudRepository redisChatCrudRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    UserRepository userRepository;

    /**
     *
     chat save: 1670073277614
     chat save: 1670073277616
     chat save: 1670073277616
     chat save: 1670073277616
     chat save: 1670073277616
     chat save: 1670073277617
     chat save: 1670073277617
     chat save: 1670073277617
     chat save: 1670073277617
     chat save: 1670073277617
     */
    @Test // 157012
    public void chatCreateTestRDB(){
        String chatRoomId = UUID.randomUUID().toString();
        chatRoomService.create(ChatRoomDto.builder()
                .chatRoomId(chatRoomId)
                .roomName("테스트 채팅창")
                .userCount(0)
                .imageUrl("imageurl")
                .build(), "user"
        );

        List<ChatEntity> entities = new ArrayList<>();
        for(int i = 0; i < 1000000;i++ ){
            ChatEntity chatEntity = ChatEntity.builder()
                    .chatRoomEntity(chatRoomRepository.findByChatRoomId(chatRoomId).get())
                    .chatId(UUID.randomUUID().toString())
                    .sender(userRepository.findByUserId("user").get())
                    .message("채팅" + i)
                    .build();
            entities.add(chatEntity);
        }
        long startTime = System.currentTimeMillis();
        chatRepository.saveAll(entities);
        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime); // 15503
    }


    @Test
    public void chatCreateRedis(){
        String chatRoomId = UUID.randomUUID().toString();
        chatRoomService.create(ChatRoomDto.builder()
                .chatRoomId(chatRoomId)
                .roomName("테스트 채팅창")
                .userCount(0)
                .imageUrl("imageurl")
                .build(), "user"
        );
        System.out.println("room create: " + System.currentTimeMillis());

        System.out.println("chat create: " + System.currentTimeMillis());
        List<RedisChatEntity> entities = new ArrayList<>();
        for(int i = 0; i < 10; i++ ) {
            RedisChatEntity chatEntity = RedisChatEntity.builder()
                    .chatRoomId(chatRoomId)
                    .chatId(UUID.randomUUID().toString())
                    .sender(userRepository.findByUserId("user").get())
                    .message("chat" + 1)
                    .build();
            entities.add(chatEntity);
//            chatEntity.setMessage("채팅" + 1);
//            redisChatRepository.createRedisChat(chatEntity);
//            System.out.println("chat save: " + System.currentTimeMillis());
        }
        long startTime = System.currentTimeMillis();
        redisChatCrudRepository.saveAll(entities);
        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }

<<<<<<< HEAD
    @Test // 12159
    public void chatRoomRedisTemplateSave(){
        String chatRoomId = UUID.randomUUID().toString();
        ChatRoomVo chatRoomVo = chatRoomService.create(ChatRoomDto.builder()
                .chatRoomId(chatRoomId)
                .roomName("테스트 채팅창")
                .userCount(0)
                .imageUrl("imageurl")
                .build(), "user"
        );

        List<ChatDto> chats = new ArrayList<>();
        for(int i = 0; i < 1000000; i++ ) {
            ChatDto chatDto = ChatDto.builder()
                    .chatRoomId(chatRoomId)
                    .message("chat" + i)
                    .senderId("user1")
                    .senderName("user")
                    .profileImageUrl("imageUrl")
                    .type(ChatDto.MessageType.TALK)
                    .build();
            chats.add(chatDto);
//            chatEntity.setMessage("채팅" + 1);
//            redisChatRepository.createRedisChat(chatEntity);
//            System.out.println("chat save: " + System.currentTimeMillis());
        }
        long startTime = System.currentTimeMillis();
        redisChatRepository.saveAll(chats, chatRoomRepository.findByChatRoomId(chatRoomVo.getChatRoomId()).get().getChatRoomCid());
        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }
}
