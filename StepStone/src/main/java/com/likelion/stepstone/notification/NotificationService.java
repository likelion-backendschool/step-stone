package com.likelion.stepstone.notification;


import com.likelion.stepstone.chat.ChatRoomOnlineFinder;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
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

    public void markAll(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("user not found"));
        List<NotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity, false);

        markAsRead(notificationEntities);
    }

    public void mark(Long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("notification not found"));

        notificationEntity.read();
    }


    public void registerOnlineChatUser(String name, String roomId) {
        Map<String, List<String>> onlineUsers = chatRoomOnlineFinder.getOnlineUsers();

        onlineUsers.computeIfAbsent(roomId, k -> new ArrayList<>());
        onlineUsers.get(roomId).add(name);
    }

    public void moveOnlineChatUser(String name, String beforeRoomId) {
        Map<String, List<String>> onlineUsers = chatRoomOnlineFinder.getOnlineUsers();
        onlineUsers.get(beforeRoomId).remove(name);

        if (onlineUsers.get(beforeRoomId).isEmpty())
            onlineUsers.remove(beforeRoomId);
    }
}
