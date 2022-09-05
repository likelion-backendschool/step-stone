package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.*;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;

    private final static String chatRoomImageUrl = "https://www.bootdey.com/app/webroot/img/Content/icons/64/PNG/64/";

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

        List<String> imageNames = getListFromRes();

        Random rand = new Random();
        String randomElement = imageNames.get(rand.nextInt(imageNames.size()));
        String imageUrl = chatRoomImageUrl + randomElement;

        chatRoomEntity.setImageUrl(imageUrl);

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

    public String findChatImageUrlByRoomId(String roomId){
        ChatRoomEntity chatRoomEntity = findByChatRoomId(roomId);

        return chatRoomEntity.getImageUrl();
    }

    public String findChatRoomNameByRoomId(String roomId){
        ChatRoomEntity chatRoomEntity = findByChatRoomId(roomId);

        return chatRoomEntity.getRoomName();
    }

    public ChatRoomEntity findByChatRoomId(String roomId){
        return chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() -> new DataNotFoundException("Chat Room Not Found"));
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

    public List<String> getListFromRes(){
        try {
            File file = ResourceUtils.getFile("classpath:static/chatRoomImageUrls.txt");

            String content = new String(Files.readAllBytes(file.toPath()));

            String[] urls = content.split(",");

            return Arrays.stream(urls).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
