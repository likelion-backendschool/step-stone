package com.likelion.stepstone.chat.redis;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chat.model.RedisChatEntity;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RedisChatRepository {
    // Redis CacheKeys
    private static final String CHAT_ROOMS = "CHAT_ROOM"; // 채팅룸 저장
    private static final String NEW_CHAT = "CHAT"; //새로운 채팅
    public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    private static final String chatRoomImageUrl = "https://www.bootdey.com/app/webroot/img/Content/icons/64/PNG/64/";
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisChatCrudRepository redisChatCrudRepository;

    private final RedisTemplate<String, ChatDto> chatRoomRedisTemplate;

    public ChatDto createChat(ChatDto chatDto) {
        ChatEntity chatEntity = fromDtoToEntity(chatDto);
        UserEntity userEntity = userRepository.findByUserId(chatDto.getSenderId()).orElseThrow(()-> new DataNotFoundException("user not found"));
        chatEntity.setSender(userEntity);
        chatEntity.setChatId(UUID.randomUUID().toString());

        RedisChatEntity redisChatEntity = convert2RedisChatEntity(chatEntity);
        createRedisChat(redisChatEntity);
//        hashOpsChat.put(NEW_CHAT, chatEntity.getChatId(), redisChatEntity);
        return ChatDto.toDto(chatEntity);
    }
    public ChatEntity fromDtoToEntity(ChatDto chatDto){
        ChatEntity chatEntity = ChatEntity.toEntity(chatDto);
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(chatDto.getChatRoomId()).orElseThrow(() -> new DataNotFoundException("Chat Room Not Exist"));
        chatEntity.setChatRoomEntity(chatRoomEntity);

        return chatEntity;
    }

    public RedisChatEntity createRedisChat(RedisChatEntity redisChatEntity){
        return redisChatCrudRepository.save(redisChatEntity);
    }
    

    public String getChatRoomImageUrl(){
        List<String> imageNames = getListFromRes();

        Random rand = new Random();
        String randomElement = imageNames.get(rand.nextInt(imageNames.size()));
        String imageUrl = chatRoomImageUrl + randomElement;

        return imageUrl;
    }

    public List<String> getListFromRes(){
        String content = "";
        ClassPathResource cpr = new ClassPathResource("static/chatRoomImageUrls.txt");

        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            content = new String(bdata, StandardCharsets.UTF_8);

            String[] urls = content.split(",");

            return Arrays.stream(urls).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RedisChatEntity convert2RedisChatEntity(ChatEntity chatEntity){
        return RedisChatEntity.builder()
                .chatId(chatEntity.getChatId())
                .message(chatEntity.getMessage())
                .chatRoomId(chatEntity.getChatRoomEntity().getChatRoomId())
                .sender(chatEntity.getSender())
                .createdAt(chatEntity.getCreatedAt())
                .build();
    }


    public void addChat(ChatDto chatDto, Long chatRoomCid){
        String key = RedisKeyGenerator.generateChatRoomKey(chatRoomCid);
        List<ChatDto> chats = findByChatRoomCid(chatRoomCid);
        chats.add(chatDto);
        chatRoomRedisTemplate.opsForList().rightPushAll(key, chats);
    }

    public List<ChatDto> findByChatRoomCid(Long chatRoomCid){
        String key = RedisKeyGenerator.generateChatRoomKey(chatRoomCid);
        Long len = chatRoomRedisTemplate.opsForList().size(key);
        return len == 0 ? new ArrayList<>() : chatRoomRedisTemplate.opsForList().range(key, 0, len-1);
    }

    public void saveAll(List<ChatDto> items, Long chatRoomCid){
        RedisSerializer keySerializer = chatRoomRedisTemplate.getStringSerializer();
        RedisSerializer valueSerializer = chatRoomRedisTemplate.getValueSerializer();

        chatRoomRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            items.forEach(chatDto -> {
                String key = RedisKeyGenerator.generateChatRoomKey(chatRoomCid);
                connection.listCommands().rPush(keySerializer.serialize(key),
                        valueSerializer.serialize(chatDto));
            });
            return null;
        });
    }
}