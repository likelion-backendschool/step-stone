package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chat.model.ChatVo;
import com.likelion.stepstone.chatroom.ChatRoomJoinRepository;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomUserJoinEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import reactor.core.publisher.Flux;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ChatService {
    //region SSE Field
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final List<Consumer<ChatDto>> chatListeners = new CopyOnWriteArrayList<>();
    //endregion
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    public void sendMessage(ChatDto chatDto) {
//        chatEntity.setCreatedAt(getCreatedAt());
        ChatEntity chatEntity = saveChat(chatDto);
        ChatDto dto = ChatDto.toDto(chatEntity);
        dto.setProfileImageUrl(chatDto.getProfileImageUrl());
        publishChat(dto);
//        messagingTemplate.convertAndSend("/sub/chat/room/" + chatDto.getChatRoomId(), chatDto);
    }

    public ChatEntity saveChat(ChatDto chatDto){
        ChatEntity chatEntity = ChatEntity.toEntity(chatDto);
        UserEntity userEntity = userRepository.findByUserId(chatDto.getSenderId()).orElseThrow(()-> new DataNotFoundException("user not found"));
        chatEntity.setSender(userEntity);
        chatEntity.setChatId(UUID.randomUUID().toString());
        return chatRepository.save(chatEntity);
    }

    public Flux<ServerSentEvent<ChatDto>> createSubscriber(String chatRoomId){
        try{
            return Flux.create(serverSentEventFluxSink -> subscribeChat(serverSentEventFluxSink::next))
                    .map(message -> ServerSentEvent.<ChatDto>builder()
                            .data((ChatDto)message)
//                        .event("new message")
                            .build());
        }catch (DataNotFoundException e){
            System.out.println(e.getMessage());
        }
        return Flux.create(serverSentEventFluxSink -> subscribeChat(serverSentEventFluxSink::next))
                .map(message -> ServerSentEvent.<ChatDto>builder()
                        .build());
    }

    private ChatDto getLastMessageOfRoom(String chatRoomId) {
        return ChatDto.toDto(chatRepository.findTopByChatRoomIdOrderByChatCidDesc(chatRoomId).orElseThrow(() -> new DataNotFoundException("Invalid Chat Room Id: %s".formatted(chatRoomId))));
    }

    public void subscribeChat(Consumer<ChatDto> chatListener){
        chatListeners.add(chatListener);
        LOGGER.info("new chat arrived total consumer: {}", chatListeners.size());
    }

    public void publishChat(ChatDto chatDto){
        chatListeners.forEach(listener -> listener.accept(chatDto));
        LOGGER.info("handling new chat: {}", chatDto);
    }

    public void enter(ChatDto chatDto) {
        ChatEntity chatEntity = ChatEntity.toEntity(chatDto);
        chatEntity.setMessage(chatEntity.getMessage() + "님이 채팅방에 참여하였습니다.");

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatEntity.getChatRoomId(), ChatDto.toDto(chatEntity));
    }

    public List<ChatDto> getHistories(String roomId) {
        List<ChatEntity> entities = chatRepository.findByChatRoomId(roomId);

        Long chatRoomCid = chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() -> new DataNotFoundException("Chat Room Not Exist")).getChatRoomCid();
        List<ChatRoomUserJoinEntity> chatRoomUserJoinEntities = chatRoomJoinRepository.findByIdChatRoomCid(chatRoomCid);

        return entities.stream()
                .map(ChatDto::toDto)
                .peek(chatDto -> {
                    for (ChatRoomUserJoinEntity chatRoomUserJoinEntity : chatRoomUserJoinEntities){
                        if(Objects.equals(chatDto.getSenderId(), chatRoomUserJoinEntity.getUserEntity().getUserId()))
                            chatDto.setProfileImageUrl(chatRoomUserJoinEntity.getProfileImageUrl());
                    }
                })
                .collect(Collectors.toList());
    }

    public String getCreatedAt(){
        Date date = new Date();
        String formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);

        return formattedDate;
    }
}
