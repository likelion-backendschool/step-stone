package com.likelion.stepstone.notification;


import com.likelion.stepstone.chat.ChatRoomOnlineFinder;
import com.likelion.stepstone.chat.event.ChatSendEvent;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ChatRoomOnlineFinder chatRoomOnlineFinder;
    private final ApplicationEventPublisher eventPublisher;


    public void markAsRead(List<NotificationEntity> notifications) {
        notifications.forEach(NotificationEntity::read);
    }

    public List<NotificationDto> readNewNotifications(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("user not found"));
        List<NotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity, false);
        markAsRead(notificationEntities);

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

    public void mark(Long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("notification not found"));

        notificationEntity.read();
    }



    public void publishNewChat(String userId, String chatRoomId) {
        UserEntity userEntity = findUserEntityByUserId(userId);

        eventPublisher.publishEvent(new ChatSendEvent(chatRoomId, userEntity));
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
}
