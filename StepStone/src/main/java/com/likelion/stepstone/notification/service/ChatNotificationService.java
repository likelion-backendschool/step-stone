package com.likelion.stepstone.notification.service;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.notification.model.*;
import com.likelion.stepstone.notification.repository.ChatNotificationRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatNotificationService {
    private final ChatNotificationRepository chatNotificationRepository;

    public List<ChatNotificationDto> readNewNotifications(UserEntity userEntity){

        List<ChatNotificationEntity> notificationEntities = chatNotificationRepository.findByUserEntityAndChecked(userEntity, false);

        List<ChatNotificationDto> notificationDtos = notificationEntities.stream().map(ChatNotificationDto::toDto).toList();

        return notificationDtos;
    }


    public List<ChatNotificationDto> getAllNotification(UserEntity userEntity) {
        List<ChatNotificationEntity> notificationEntities = chatNotificationRepository.findAllByUserEntityOrderByIdDesc(userEntity);
        List<ChatNotificationDto> notificationDtos = notificationEntities.stream().map(ChatNotificationDto::toDto).toList();
        return notificationDtos;
    }

//    public void publishNewChat(String userId, List<UserEntity> users, ChatRoomEntity chatRoomEntity) {
//        for(UserEntity userEntity : users){
//            if(userEntity.getUserId().equals(userId)) continue;
////            eventPublisher.publishEvent(new ChatSendEvent(chatRoomId, userEntity));
//            handleChatSendEvent( chatRoomEntity, userEntity);
//        }
//    }
//
//    public String getCurrentUriPath(String currentURI){
//        String[] paths = currentURI.split("/");
//
//        StringBuilder sb = new StringBuilder();
//        for(int i = 3; i < paths.length; i++){
//            sb.append(paths[i]);
//            sb.append("/");
//        }
//        sb.deleteCharAt(sb.length()-1);
//        System.out.println(sb);
//
//        return sb.toString();
//    }
//    public void handleChatSendEvent(ChatRoomEntity chatRoomEntity, UserEntity userEntity){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
//        log.info(chatRoomEntity.getRoomName() + ": new message arrived");
//
//        ChatNotificationEntity notificationEntity = createChatNotification(chatRoomEntity, userEntity);
//        // TODO DB에 Notification 정보 저장
//
//        if(!chatNotificationRepository.existsByUserEntityAndNotificationTypeAndChecked(userEntity, NotificationType.CHAT_SEND, false))
//            chatNotificationRepository.save(notificationEntity);
//    }
//    private ChatNotificationEntity createChatNotification(ChatRoomEntity chatRoomEntity, UserEntity userEntity){
//
//        NotificationEntity notificationEntity =  NotificationEntity.builder()
//                .title("새로운 채팅")
//                .message(chatRoomEntity.getRoomName() + " 채팅방에 새로운 채팅이 도착했습니다.")
//                .checked(false)
//                .notificationType(NotificationType.CHAT_SEND)
//                .userEntity(userEntity)
//                .build();
//
//        ChatNotificationEntity chatNotificationEntity = (ChatNotificationEntity) notificationEntity;
//        chatNotificationEntity.setPublisher(userEntity);
//        chatNotificationEntity.setChatRoomEntity(chatRoomEntity);
//
//        return chatNotificationEntity;
//    }
}
