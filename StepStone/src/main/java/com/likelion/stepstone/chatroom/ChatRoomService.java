package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.*;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;

    /**
     * TODO
     * 사용중인 사용자, 개발자가 채팅방에 포함되도록 변경 필요
     *
     * @return
     */
    public ChatRoomVo create(ChatRoomDto chatRoomDto, String principalName) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toEntity(chatRoomDto);

        chatRoomEntity.setUserCount(1);
        UserEntity userEntity = findByUserId(principalName);
        if(chatRoomEntity.getUsers() == null)
            chatRoomEntity.setUsers(new HashSet<>());

        chatRoomEntity.getUsers().add(userRepository.findById(userEntity.getUserCid()).orElseThrow(() -> new DataNotFoundException("user not found")));

        chatRoomRepository.save(chatRoomEntity);

        return ChatRoomVo.toVo(ChatRoomDto.toDto(chatRoomEntity));
    }


    /**
     * TODO
     * 사용중인 사용자, 개발자가 채팅방에 참가중인지 확인 필요
     *
     * @return
     */
    public List<ChatRoomDto> findAll(String principalName){
        Long userCid = findByUserId(principalName).getUserCid();
        List<ChatRoomEntity> entities = chatRoomRepository.findByUsers_UserCid(userCid);

        return entities.stream().map(ChatRoomDto::toDto).collect(Collectors.toList());
    }

    public UserEntity findByUserId(String user_id){
        return userRepository.findByUserId(user_id).orElseThrow(() -> new DataNotFoundException("User Name Not Found"));

    }

    public String getUsername(String principalName){
        return userRepository.findByUserId(principalName).orElseThrow(()-> new DataNotFoundException("User Name Not Found")).getName();
    }

    public void invite(String roomId, String userId) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() -> new DataNotFoundException("Invalid room Id"));
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(()-> new DataNotFoundException("User Not Found"));
        ChatRoomUserJoinId chatRoomUserJoinId = ChatRoomUserJoinId.builder().chatRoomCid(chatRoomEntity.getChatRoomCid()).userCid(userEntity.getUserCid()).build();
        String profileImageDefaultUrl = "https://www.bootdey.com/img/Content/avatar/";
        int randomInt = (int)(Math.random()*7);
        String profileImageUrl = profileImageDefaultUrl + "avatar" + randomInt + ".png";
        chatRoomJoinRepository.save(ChatRoomUserJoinEntity.builder()
                        .id(chatRoomUserJoinId)
                .userEntity(userEntity)
                .chatRoomEntity(chatRoomEntity)
                        .profileImageUrl(profileImageUrl)
                .build());
    }
}
