package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.event.ChatSendEvent;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chat.model.ChatVo;
import com.likelion.stepstone.chatroom.ChatRoomJoinRepository;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.event.ChatRoomInviteEvent;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomUserJoinEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ChatService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ApplicationEventPublisher eventPublisher;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    public void sendMessage(ChatDto chatDto) {
        ChatEntity chatEntity = fromDtoToEntity(chatDto);

        UserEntity userEntity = userRepository.findByUserId(chatDto.getSenderId()).orElseThrow(()-> new DataNotFoundException("user not found"));
        chatEntity.setSender(userEntity);
        chatEntity.setChatId(UUID.randomUUID().toString());
//        chatEntity.setCreatedAt(getCreatedAt());
        saveMessage(chatEntity);
        chatEventPublish(chatEntity, userEntity);
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatEntity.getChatRoomEntity().getChatRoomId(), chatDto);
    }

    public void saveMessage(ChatEntity chatEntity){
        chatRepository.save(chatEntity);
    }

    private void chatEventPublish( ChatEntity chatEntity, UserEntity userEntity){
        eventPublisher.publishEvent(new ChatSendEvent(chatEntity, userEntity));
    }
    public void enter(ChatDto chatDto) {
        ChatEntity chatEntity = fromDtoToEntity(chatDto);
        chatEntity.setMessage(chatEntity.getMessage() + "님이 채팅방에 참여하였습니다.");

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatEntity.getChatRoomEntity().getChatRoomId(), ChatDto.toDto(chatEntity));
    }

    public List<ChatDto> getHistories(String roomId) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() -> new DataNotFoundException("Chat Room Not Exist"));
        List<ChatEntity> entities = chatRepository.findByChatRoomEntity(chatRoomEntity);

        Long chatRoomCid = chatRoomEntity.getChatRoomCid();
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

    public ChatEntity fromDtoToEntity(ChatDto chatDto){
        ChatEntity chatEntity = ChatEntity.toEntity(chatDto);
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(chatDto.getChatRoomId()).orElseThrow(() -> new DataNotFoundException("Chat Room Not Exist"));
        chatEntity.setChatRoomEntity(chatRoomEntity);

        return chatEntity;
    }
}
