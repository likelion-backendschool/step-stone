package com.likelion.stepstone.notification;


import com.likelion.stepstone.chat.ChatRoomOnlineFinder;
import com.likelion.stepstone.chat.event.ChatSendEvent;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.ChatRoomJoinRepository;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ApplicationEventPublisher eventPublisher;


    public void markAsRead(List<NotificationEntity> notifications) {
        notifications.forEach(NotificationEntity::read);
    }

    public List<NotificationDto> readNewNotifications(String userId){

        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if(userEntity.isEmpty()) return new ArrayList<>();

        List<NotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity.get(), false);

        List<NotificationDto> notificationDtos = notificationEntities.stream().map(NotificationDto::toDto).toList();

        return notificationDtos;
    }

    public UserEntity findUserEntityByUserId(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("user not found"));
        return  userEntity;
    }

    public void markAll(String userId) {
        UserEntity userEntity = findUserEntityByUserId(userId);
        List<NotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity, false);

        markAsRead(notificationEntities);
    }

    public void markAsRead(Long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("notification not found"));

        notificationEntity.read();
    }


    public void publishNewChat(String userId, List<UserEntity> users, ChatRoomEntity chatRoomEntity) {
        for(UserEntity userEntity : users){
            if(userEntity.getUserId().equals(userId)) continue;
//            eventPublisher.publishEvent(new ChatSendEvent(chatRoomId, userEntity));
            handleChatSendEvent( chatRoomEntity, userEntity);
        }
    }

    public String getCurrentUriPath(String currentURI){
        String[] paths = currentURI.split("/");

        StringBuilder sb = new StringBuilder();
        for(int i = 3; i < paths.length; i++){
            sb.append(paths[i]);
            sb.append("/");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb);

        return sb.toString();
    }
    public void handleChatSendEvent(ChatRoomEntity chatRoomEntity, UserEntity userEntity){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
        log.info(chatRoomEntity.getRoomName() + ": new message arrived");

        NotificationEntity notificationEntity = createNotification(chatRoomEntity, userEntity);
        // TODO DB에 Notification 정보 저장

        if(!notificationRepository.existsByUserEntityAndNotificationTypeAndChecked(userEntity, NotificationType.CHAT_SEND, false))
            notificationRepository.save(notificationEntity);
    }
    private ChatNotificationEntity createNotification(ChatRoomEntity chatRoomEntity, UserEntity userEntity){

        NotificationEntity notificationEntity =  NotificationEntity.builder()
                .title("새로운 채팅")
                .message(chatRoomEntity.getRoomName() + " 채팅방에 새로운 채팅이 도착했습니다.")
                .checked(false)
                .notificationType(NotificationType.CHAT_SEND)
                .userEntity(userEntity)
                .build();

        ChatNotificationEntity chatNotificationEntity = (ChatNotificationEntity) notificationEntity;
        chatNotificationEntity.setPublisher(userEntity);
        chatNotificationEntity.setChatRoomEntity(chatRoomEntity);

        return chatNotificationEntity;
    }


}
