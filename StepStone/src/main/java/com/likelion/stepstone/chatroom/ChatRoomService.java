package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomVo;
import com.likelion.stepstone.user.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    public ChatRoomVo create(ChatRoomDto chatRoomDto) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toEntity(chatRoomDto);

        chatRoomEntity.getUsers().add(userRepository.findById(1l).orElseThrow(() -> new DataNotFoundException("user not found")));

        chatRoomRepository.save(chatRoomEntity);

        return ChatRoomVo.toVo(ChatRoomDto.toDto(chatRoomEntity));
    }


    /**
     * TODO
     * 사용중인 사용자, 개발자가 채팅방에 참가중인지 확인 필요
     *
     * @return
     */
    public List<ChatRoomDto> findAll(){
        List<ChatRoomEntity> entities = chatRoomRepository.findByUsers_UserCid(1l);

        return entities.stream().map(entity -> ChatRoomDto.toDto(entity)).collect(Collectors.toList());
    }


}
