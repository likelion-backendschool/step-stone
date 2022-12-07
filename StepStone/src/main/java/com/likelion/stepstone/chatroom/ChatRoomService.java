package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.event.ChatRoomCreatedEvent;
import com.likelion.stepstone.chatroom.event.ChatRoomInquireEvent;
import com.likelion.stepstone.chatroom.event.ChatRoomInviteEvent;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.*;
import com.likelion.stepstone.notification.repository.NotificationRepository;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final NotificationRepository notificationRepository;
    private final static String chatRoomImageUrl = "https://www.bootdey.com/app/webroot/img/Content/icons/64/PNG/64/";

    public ChatRoomVo create(ChatRoomDto chatRoomDto, String principalName) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toEntity(chatRoomDto);

        chatRoomEntity.setUserCount(1);
        UserEntity userEntity = findByUserId(principalName);
        if(chatRoomEntity.getUsers() == null)
            chatRoomEntity.setUsers(new HashSet<>());

        chatRoomEntity.getUsers().add(userRepository.findById(userEntity.getUserCid()).orElseThrow(() -> new DataNotFoundException("user not found")));

        String imageUrl = getChatRoomImageUrl();

        chatRoomEntity.setImageUrl(imageUrl);

        chatRoomRepository.save(chatRoomEntity);
//        createEventPublish(chatRoomEntity, userEntity);

        String profileImage = pickProfileImage();
        ChatRoomUserJoinEntity chatRoomUserJoinEntity = chatRoomJoinRepository.findByChatRoomEntityAndUserEntity(chatRoomEntity, userEntity).orElseThrow(() -> new DataNotFoundException("chat room creation error"));
        chatRoomUserJoinEntity.setProfileImageUrl(profileImage);

        chatRoomJoinRepository.save(chatRoomUserJoinEntity);
        return ChatRoomVo.toVo(ChatRoomDto.toDto(chatRoomEntity));
    }

    public void invite(String roomId, String userId, String publisherId){
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() -> new DataNotFoundException("Invalid room Id"));
        UserEntity userEntity = findByUserId(userId);
        UserEntity publisher = findByUserId(publisherId);

        inviteEventPublish(chatRoomEntity, userEntity, publisher);
    }

    public void exit(String userId, String chatRoomId) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new DataNotFoundException("Invalid room Id"));
        UserEntity userEntity = findByUserId(userId);
        chatRoomEntity.setUserCount(chatRoomEntity.getUserCount()-1);

        ChatRoomUserJoinEntity chatRoomUserJoinEntity = chatRoomJoinRepository.findByChatRoomEntityAndUserEntity(chatRoomEntity, userEntity).get();
        chatRoomJoinRepository.deleteById(chatRoomUserJoinEntity.getId());
    }

    public void inquire(String developerId, PostEntity postEntity) {
        UserEntity developer = findByUserId(developerId);
        UserEntity user = postEntity.getUser();

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.getNullInstance();


        inquireEventPublish(developer, user, chatRoomEntity);
    }

    public List<ChatRoomDto> searchByUserIdAndChatRoomName(String name, String chatRoomName) {
        Long userCid = findByUserId(name).getUserCid();

        List<ChatRoomUserJoinEntity> chatRoomUserJoinEntities = chatRoomJoinRepository.findByIdUserCid(userCid);

        List<ChatRoomEntity> chatRoomEntities = new ArrayList<>();
        for(ChatRoomUserJoinEntity chatRoomUserJoinEntity : chatRoomUserJoinEntities){
            ChatRoomEntity chatRoomEntity = chatRoomUserJoinEntity.getChatRoomEntity();
            if(chatRoomEntity.getRoomName().contains(chatRoomName))
                chatRoomEntities.add(chatRoomEntity);
        }

        return chatRoomEntities.stream().map(ChatRoomDto::toDto).collect(Collectors.toList());
    }

    public void confirmInvite(String roomId, String userId) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() -> new DataNotFoundException("Invalid room Id"));
        //인원 수 추가
        chatRoomEntity.setUserCount(chatRoomEntity.getUserCount()+1);
        chatRoomRepository.updateUserCount(chatRoomEntity.getUserCount(), chatRoomEntity.getChatRoomCid() );

        UserEntity userEntity = findByUserId(userId);
        ChatRoomUserJoinId chatRoomUserJoinId = ChatRoomUserJoinId.builder().chatRoomCid(chatRoomEntity.getChatRoomCid()).userCid(userEntity.getUserCid()).build();
        String profileImageDefaultUrl = "https://www.bootdey.com/img/Content/avatar/";
        int randomInt = (int)(Math.random()*6) + 1; //0 제외
        String profileImageUrl = profileImageDefaultUrl + "avatar" + randomInt + ".png";
        chatRoomJoinRepository.save(ChatRoomUserJoinEntity.builder()
                .id(chatRoomUserJoinId)
                .userEntity(userEntity)
                .chatRoomEntity(chatRoomEntity)
                .profileImageUrl(profileImageUrl)
                .build());
    }

    public void confirmInquiry(String userId, String publisherId, String chatRoomName){

        UserEntity userEntity = findByUserId(userId);
        UserEntity publisher = findByUserId(publisherId);

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .roomName(chatRoomName)
                .postCid(-1l)
                .imageUrl(getChatRoomImageUrl())
                .userCount(2)
                .build();

        if(chatRoomEntity.getUsers() == null)
            chatRoomEntity.setUsers(new HashSet<>());

        chatRoomEntity.getUsers().add(userRepository.findById(userEntity.getUserCid()).orElseThrow(() -> new DataNotFoundException("user not found")));
        chatRoomEntity.getUsers().add(userRepository.findById(publisher.getUserCid()).orElseThrow(() -> new DataNotFoundException("user not found")));

        chatRoomRepository.save(chatRoomEntity);
        createEventPublish(chatRoomEntity, userEntity);

        String profileImage = pickProfileImage();
        ChatRoomUserJoinEntity chatRoomUserJoinEntity = chatRoomJoinRepository.findByChatRoomEntityAndUserEntity(chatRoomEntity, userEntity).orElseThrow(() -> new DataNotFoundException("chat room creation error"));
        chatRoomUserJoinEntity.setProfileImageUrl(profileImage);

        chatRoomJoinRepository.save(chatRoomUserJoinEntity);

        profileImage = pickProfileImage();
        chatRoomUserJoinEntity = chatRoomJoinRepository.findByChatRoomEntityAndUserEntity(chatRoomEntity, publisher).orElseThrow(() -> new DataNotFoundException("chat room creation error"));
        chatRoomUserJoinEntity.setProfileImageUrl(profileImage);

        chatRoomJoinRepository.save(chatRoomUserJoinEntity);

    }

    private String pickProfileImage(){
        String profileImageDefaultUrl = "https://www.bootdey.com/img/Content/avatar/";
        int randomInt = (int)(Math.random()*6) + 1; //0 제외
        String profileImageUrl = profileImageDefaultUrl + "avatar" + randomInt + ".png";

        return profileImageUrl;
    }

    //region publish
    private void createEventPublish( ChatRoomEntity chatRoomEntity, UserEntity userEntity ){
        eventPublisher.publishEvent(new ChatRoomCreatedEvent(chatRoomEntity, userEntity));
    }

    private void inviteEventPublish( ChatRoomEntity chatRoomEntity, UserEntity userEntity, UserEntity publisher ){
        eventPublisher.publishEvent(new ChatRoomInviteEvent(chatRoomEntity, userEntity, publisher));
    }

    public void inquireEventPublish(UserEntity developer, UserEntity user, ChatRoomEntity chatRoomEntity){
        eventPublisher.publishEvent(new ChatRoomInquireEvent(developer, user, chatRoomEntity));
    }
    //endregion

    //region find
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

    public UserEntity findByUserId(String userId){
        return userRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("User Name Not Found"));
    }

    public List<ChatRoomDto> findAll(String principalName){
        Long userCid = findByUserId(principalName).getUserCid();
        List<ChatRoomEntity> entities = chatRoomRepository.findByUsers_UserCid(userCid);

        return entities.stream().map(ChatRoomDto::toDto).collect(Collectors.toList());
    }

    public List<UserEntity> findAllUserInChatRoom(String chatRoomId){
        ChatRoomEntity chatRoomEntity = findByChatRoomId(chatRoomId);
        List<ChatRoomUserJoinEntity> chatRoomUserJoinEntities = chatRoomJoinRepository.findByChatRoomEntity(chatRoomEntity);
        List<UserEntity> users = new ArrayList<>();
        for(ChatRoomUserJoinEntity chatRoomUserJoinEntity : chatRoomUserJoinEntities)
        {
            users.add(chatRoomUserJoinEntity.getUserEntity());
        }

        return users;

    }

    public List<UserDto> findAllUserDtoInChatRoom(String chatRoomId){
        return findAllUserInChatRoom(chatRoomId).stream().map(UserDto::toDto).toList();
    }

    public List<String> findAllRoomId(String userId) {
        List<ChatRoomDto> dtos = findAll(userId);
        List<String> allRoomId = dtos.stream().map(ChatRoomDto::getChatRoomId).toList();

        return allRoomId;
    }
    //endregion

    //region get
    public String getUsername(String principalName){
        return userRepository.findByUserId(principalName).orElseThrow(()-> new DataNotFoundException("User Name Not Found")).getName();
    }

    public String getChatRoomImageUrl(){
        List<String> imageNames = getListFromRes();

        Random rand = new Random();
        String randomElement = imageNames.get(rand.nextInt(imageNames.size()));
        String imageUrl = chatRoomImageUrl + randomElement;

        return imageUrl;
    }

    public String getCreationAvatar(String userId, String chatRoomId){
        ChatRoomEntity chatRoomEntity = findByChatRoomId(chatRoomId);
        UserEntity userEntity = findByUserId(userId);
        ChatRoomUserJoinEntity chatRoomUserJoinEntity = chatRoomJoinRepository.findByChatRoomEntityAndUserEntity(chatRoomEntity, userEntity).orElseThrow(() -> new DataNotFoundException("chatRoom not found"));
        return chatRoomUserJoinEntity.getProfileImageUrl();
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
    //endregion

    //region check
    public boolean isUserExist(String userId){
        return userRepository.findByUserId(userId).isPresent();
    }

    public boolean isUserAlreadyIn(String userId, String chatRoomId){
        UserEntity userEntity = findByUserId(userId);
        ChatRoomEntity chatRoomEntity = findByChatRoomId(chatRoomId);

        return chatRoomJoinRepository.existsByUserEntityAndChatRoomEntity(userEntity, chatRoomEntity);

    }
    //endregion
}
