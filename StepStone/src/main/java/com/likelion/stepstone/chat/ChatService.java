package com.likelion.stepstone.chat;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ChatService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    public void sendMessage(ChatDto chatDto) {
        ChatEntity chatEntity = ChatEntity.toEntity(chatDto);
        UserEntity userEntity = userRepository.findById(1l).orElseThrow(()-> new DataNotFoundException("user not found"));
        chatEntity.setSender(userEntity);
        chatEntity.setChatId(UUID.randomUUID().toString());

//        if(!chatRoomEntity.getUsers().contains(chatEntity.getSender())){
//            throw new DataNotFoundException("참가 중인 유저가 아님");
//        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatDto.getChatRoomId(), ChatDto.toDto(chatEntity));
    }

    public void enter(ChatDto chatDto) {
        ChatEntity chatEntity = ChatEntity.toEntity(chatDto);
        chatEntity.setMessage(chatEntity.getMessage() + "님이 채팅방에 참여하였습니다.");

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatEntity.getChatRoomId(), ChatDto.toDto(chatEntity));
    }

    public List<ChatDto> getHistories(String roomId) {
        List<ChatEntity> entities = chatRepository.findByChatRoomId(roomId);

        return entities.stream().map(ChatDto::toDto).collect(Collectors.toList());
    }
}
